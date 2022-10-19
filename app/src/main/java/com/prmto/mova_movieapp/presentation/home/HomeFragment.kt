package com.prmto.mova_movieapp.presentation.home

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.presentation.home.recyler.NowPlayingRecyclerAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.PopularMoviesAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.PopularTvSeriesAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.TopRatedMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val nowPlayingAdapter: NowPlayingRecyclerAdapter,
    private val popularMoviesAdapter: PopularMoviesAdapter,
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter,
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter
) : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding


    private val viewModel: HomeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding


        val connMgr =
            requireContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connMgr.activeNetwork != null) {

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
                        }
                    }

                    launch {
                        val tvGenreList = viewModel.getTvGenreList().genres
                        if (tvGenreList.isNotEmpty()) {
                            popularTvSeriesAdapter.passMovieGenreList(tvGenreList)
                        }
                    }
                }

            }
        }
        setupRecyclerAdapters()
        setAdaptersClickListener()


    }


    private fun setupRecyclerAdapters() {
        if (binding != null) {
            binding?.apply {
                nowPlayingRecyclerView.adapter = nowPlayingAdapter
                nowPlayingRecyclerView.setAlpha(true)
                popularMoviesRecyclerView.adapter = popularMoviesAdapter
                topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
                popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter
            }
        }

    }

    private fun setAdaptersClickListener() {
        popularMoviesAdapter.setOnItemClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.movie = movie
            findNavController().navigate(action)
        }
        popularTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.tvSeries = tvSeries
            findNavController().navigate(action)
        }
        topRatedMoviesAdapter.setOnItemClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet()
            action.movie = movie
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}