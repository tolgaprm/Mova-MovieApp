package com.prmto.mova_movieapp.presentation.home


import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.presentation.home.recyler.*
import com.prmto.mova_movieapp.util.getCountryIsoCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Caught Exception $throwable")
    }

    @Inject
    lateinit var nowPlayingAdapter: NowPlayingRecyclerAdapter

    @Inject
    lateinit var popularMoviesAdapter: PopularMoviesAdapter

    @Inject
    lateinit var popularTvSeriesAdapter: PopularTvSeriesAdapter

    @Inject
    lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter

    @Inject
    lateinit var topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding
        observeNetworkConnectivity()
        collectDataLifecycleAware()
        addCallback()
        setupListenerSeeAllClickEvents()
        setupRecyclerAdapters()
        setAdaptersClickListener()
        binding.btnNavigateUp.setOnClickListener {
            hideRecyclerViewSeeAll()
        }
    }

    private fun observeNetworkConnectivity() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.observeNetworkConnectivity().collectLatest { it ->
                        if (it == ConnectivityObserver.Status.Unavaliable || it == ConnectivityObserver.Status.Lost) {
                            return@collectLatest
                        } else if (it == ConnectivityObserver.Status.Avaliable) {
                            retryAllPagingAdapter()
                        }
                    }
                }
            }
        }
    }

    private fun retryAllPagingAdapter() {
        nowPlayingAdapter.retry()
        popularMoviesAdapter.retry()
        popularTvSeriesAdapter.retry()
        topRatedMoviesAdapter.retry()
        topRatedTvSeriesAdapter.retry()
    }

    private fun collectDataLifecycleAware() =
        viewLifecycleOwner.lifecycleScope.launch(handler) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                supervisorScope {
                    launch {
                        viewModel.getLanguageIsoCode().collect {
                            viewModel.setLanguageIsoCode(it)
                        }
                    }

                    launch {
                        val countryIsoCode = requireContext().getCountryIsoCode().uppercase()
                        viewModel.setCountryIsoCode(countryIsoCode)
                    }

                    launch {
                        viewModel.getNowPlayingMovies().collectLatest { pagingData ->
                            nowPlayingAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getPopularMovies()
                            .collectLatest { pagingData ->
                                popularMoviesAdapter.submitData(pagingData)
                            }
                    }

                    launch {
                        viewModel.getTopRatedMovies()
                            .collectLatest { pagingData ->
                                topRatedMoviesAdapter.submitData(pagingData)
                            }
                    }

                    launch {
                        viewModel.getPopularTvSeries()
                            .collectLatest { pagingData ->
                                popularTvSeriesAdapter.submitData(pagingData)
                            }
                    }

                    launch {
                        collectMovieGenreList()
                    }

                    launch {
                        collectTvGenreList()
                    }

                    launch {
                        viewModel.getTopRatedTvSeries().collectLatest { pagingData ->
                            topRatedTvSeriesAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.isShowsRecyclerViewSeeAllSection.collectLatest { isShowsSeeAllPage ->
                            if (isShowsSeeAllPage) {
                                viewModel.latestShowsRecyclerViewSeeAllSectionToolBarText.collectLatest { textId ->
                                    val adapter = getAdapterForSeeAllSection(textId)
                                    showRecyclerViewSeeAll(textId)
                                    binding?.let {
                                        it.recyclerViewSeeAll.adapter = adapter
                                    }
                                }
                            } else {
                                viewModel.setShowsRecyclerViewSeeAllSection(false)
                                hideRecyclerViewSeeAll()
                            }
                        }
                    }
                }
            }
        }

    private suspend fun collectTvGenreList() {
        val tvGenreList = viewModel.getTvGenreList().genres
        if (tvGenreList.isNotEmpty()) {
            popularTvSeriesAdapter.passMovieGenreList(tvGenreList)
        }
    }

    private suspend fun collectMovieGenreList() {
        val genreList = viewModel.getMovieGenreList().genres
        if (genreList.isNotEmpty()) {
            nowPlayingAdapter.passMovieGenreList(genreList)
            popularMoviesAdapter.passMovieGenreList(genreList)
            topRatedMoviesAdapter.passMovieGenreList(genreList)
            topRatedTvSeriesAdapter.passMovieGenreList(genreList)
        }
    }

    private fun hideRecyclerViewSeeAll() {
        viewModel.setShowsRecyclerViewSeeAllSection(false)
        binding?.let {
            it.recyclerViewSeeAllSection.visibility = View.GONE
            it.scrollView.visibility = View.VISIBLE
            it.scrollView.animation = slideInLeftAnim()
            it.recyclerViewSeeAll.removeAllViews()
        }
    }

    private fun addCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding?.let {
                    if (it.recyclerViewSeeAll.visibility == View.VISIBLE) {
                        hideRecyclerViewSeeAll()
                    } else {
                        findNavController().popBackStack()
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setupListenerSeeAllClickEvents() {
        binding?.let {
            it.apply {
                nowPlayingSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.now_playing)
                    recyclerViewSeeAll.adapter = nowPlayingAdapter
                }
                popularMoviesSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.popular_movies)
                    recyclerViewSeeAll.adapter = popularMoviesAdapter
                }
                popularTvSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.popular_tv_series)
                    recyclerViewSeeAll.adapter = popularTvSeriesAdapter
                }
                topRatedMoviesSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.top_rated_movies)
                    recyclerViewSeeAll.adapter = topRatedMoviesAdapter
                }
                topRatedTvSeriesSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.top_rated_tv_series)
                    recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
                }
            }
        }
    }

    private fun setSeeAllPage(@StringRes toolbarTextId: Int) {
        showRecyclerViewSeeAll(toolbarTextId)
        viewModel.setLatestShowsRecyclerViewSeeAllSection(toolbarTextId)
    }

    private fun showRecyclerViewSeeAll(@StringRes toolBarTextId: Int) {
        val context = requireContext()
        viewModel.setShowsRecyclerViewSeeAllSection(true)
        binding?.let {
            it.apply {
                scrollView.visibility = View.GONE
                recyclerViewSeeAllSection.visibility = View.VISIBLE
                toolbarText.text = context.getString(toolBarTextId)
                recyclerViewSeeAllSection.animation = slideInLeftAnim()
                recyclerViewSeeAll.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun slideInLeftAnim(): Animation =
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)

    private fun setupRecyclerAdapters() {
        if (binding != null) {
            binding?.apply {
                nowPlayingRecyclerView.adapter = nowPlayingAdapter
                nowPlayingRecyclerView.setAlpha(true)
                popularMoviesRecyclerView.adapter = popularMoviesAdapter.withLoadStateFooter(
                    footer = MovaLoadStateAdapter() { popularMoviesAdapter.retry() }
                )
                topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter.withLoadStateFooter(
                    footer = MovaLoadStateAdapter { topRatedMoviesAdapter.retry() }
                )
                popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter.withLoadStateFooter(
                    footer = MovaLoadStateAdapter { popularTvSeriesAdapter.retry() }
                )
                topRatedTvSeriesRecyclerView.adapter = topRatedTvSeriesAdapter.withLoadStateFooter(
                    footer = MovaLoadStateAdapter { topRatedTvSeriesAdapter.retry() }
                )
            }
        }
    }

    private fun getAdapterForSeeAllSection(textId: Int): PagingDataAdapter<out Parcelable, out RecyclerView.ViewHolder> {
        return when (textId) {
            R.string.now_playing -> nowPlayingAdapter
            R.string.popular_movies -> popularMoviesAdapter
            R.string.popular_tv_series -> popularTvSeriesAdapter
            R.string.top_rated_movies -> topRatedMoviesAdapter
            R.string.top_rated_tv_series -> topRatedTvSeriesAdapter
            else -> nowPlayingAdapter
        }
    }

    private fun setAdaptersClickListener() {

        popularMoviesAdapter.setOnItemClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.movie = movie
            findNavController().navigate(action)
        }

        topRatedMoviesAdapter.setOnItemClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.movie = movie
            findNavController().navigate(action)
        }

        nowPlayingAdapter.setOnClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.movie = movie
            findNavController().navigate(action)
        }

        popularTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.tvSeries = tvSeries
            findNavController().navigate(action)
        }

        topRatedTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.tvSeries = tvSeries
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}