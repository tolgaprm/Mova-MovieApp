package com.prmto.mova_movieapp.presentation.home


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.presentation.home.recyler.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding


    @Inject
    lateinit var imageLoader: ImageLoader

    private val nowPlayingAdapter: NowPlayingRecyclerAdapter by lazy {
        NowPlayingRecyclerAdapter(
            imageLoader
        )
    }
    private val popularMoviesAdapter: PopularMoviesAdapter by lazy {
        PopularMoviesAdapter(
            imageLoader
        )
    }
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter by lazy {
        TopRatedMoviesAdapter(
            imageLoader
        )
    }
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter by lazy {
        PopularTvSeriesAdapter(
            imageLoader
        )
    }
    private val topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter by lazy {
        TopRatedTvSeriesAdapter(
            imageLoader
        )
    }


    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding

        addCallback()
        setupListenerSeeAllClickEvents()
        setupRecyclerAdapters()
        observeNetworkConnectivity()
        setAdaptersClickListener()

        binding.btnNavigateUp.setOnClickListener {
            hideRecyclerViewSeeAll()
        }
    }

    private fun addCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hideRecyclerViewSeeAll()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun showRecyclerViewSeeAll(@StringRes toolBarTextId: Int) {
        val context = requireContext()

        binding?.let {
            it.apply {
                scrollView.visibility = View.GONE
                recyclerViewSeeAllSection.visibility = View.VISIBLE
                toolbarText.text = context.getString(toolBarTextId)
                recyclerViewSeeAll.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }


    private fun hideRecyclerViewSeeAll() {
        binding?.let {
            it.recyclerViewSeeAllSection.visibility = View.GONE
            it.scrollView.visibility = View.VISIBLE
            it.recyclerViewSeeAll.removeAllViews()
        }
    }

    private fun setupListenerSeeAllClickEvents() {
        binding?.let {
            it.apply {
                nowPlayingSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.now_playing)
                    recyclerViewSeeAll.adapter = nowPlayingAdapter
                }
                popularMoviesSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.popular_movies)
                    recyclerViewSeeAll.adapter = popularMoviesAdapter
                }
                popularTvSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.popular_tv_series)
                    recyclerViewSeeAll.adapter = popularTvSeriesAdapter
                }
                topRatedMoviesSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.top_rated_movies)
                    recyclerViewSeeAll.adapter = topRatedMoviesAdapter
                }
                topRatedTvSeriesSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.top_rated_tv_series)
                    recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
                }
            }
        }

    }

    private fun observeNetworkConnectivity() {
        var job: Job? = null
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.observeNetworkConnectivity().collectLatest { it ->
                        if (it == ConnectivityObserver.Status.Unavaliable || it == ConnectivityObserver.Status.Lost) {
                            viewModel.showSnackbar()
                            job?.cancel()
                        } else if (it == ConnectivityObserver.Status.Avaliable) {
                            job?.cancel()
                            job = collectDataLifecycleAware()
                        }
                    }
                }

                launch {
                    viewModel.showSnackBarNoInternetConnectivity.collectLatest {
                        if (it.isNotEmpty()) {
                            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

    }

    private fun setupRecyclerAdapters() {
        if (binding != null) {
            binding?.apply {
                nowPlayingRecyclerView.adapter = nowPlayingAdapter
                nowPlayingRecyclerView.setAlpha(true)
                popularMoviesRecyclerView.adapter = popularMoviesAdapter
                topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
                popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter
                topRatedTvSeriesRecyclerView.adapter = topRatedTvSeriesAdapter
            }
        }

    }

    private fun collectDataLifecycleAware() =
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.getLanguage().collect {
                        viewModel.setLanguage(it)
                    }
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
                    val genreList = viewModel.getMovieGenreList().genres
                    if (genreList.isNotEmpty()) {
                        nowPlayingAdapter.passMovieGenreList(genreList)
                        popularMoviesAdapter.passMovieGenreList(genreList)
                        topRatedMoviesAdapter.passMovieGenreList(genreList)
                        topRatedTvSeriesAdapter.passMovieGenreList(genreList)
                    }
                }

                launch {
                    val tvGenreList = viewModel.getTvGenreList().genres
                    if (tvGenreList.isNotEmpty()) {
                        popularTvSeriesAdapter.passMovieGenreList(tvGenreList)
                    }
                }

                launch {
                    viewModel.getTopRatedTvSeries().collectLatest { pagingData ->
                        topRatedTvSeriesAdapter.submitData(pagingData)
                    }
                }


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