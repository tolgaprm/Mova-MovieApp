package com.prmto.mova_movieapp.presentation.home

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.presentation.home.recyler.NowPlayingRecyclerAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.PopularMoviesRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val nowPlayingAdapter: NowPlayingRecyclerAdapter,
    private val popularMoviesRecyclerView: PopularMoviesRecyclerView
) : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null


    private val viewModel: HomeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)


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
                                popularMoviesRecyclerView.submitData(pagingData)
                            }
                    }

                    launch {
                        val genreList = viewModel.getMovieGenreList().genres
                        if (genreList.isNotEmpty()) {
                            nowPlayingAdapter.passMovieGenreList(genreList)
                            popularMoviesRecyclerView.passMovieGenreList(genreList)
                        }
                    }


                }

            }
        }




        binding.nowPlayingRecyclerView.adapter = nowPlayingAdapter
        binding.nowPlayingRecyclerView.setAlpha(true)
        binding.popularMoviesRecyclerView.adapter = popularMoviesRecyclerView


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}