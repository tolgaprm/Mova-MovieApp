package com.prmto.mova_movieapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.presentation.home.recyler.NowPlayingRecyclerAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.PopularMoviesRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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


        lifecycleScope.launchWhenCreated {

            viewModel.getLanguage().collectLatest { language ->
                viewModel.getNowPlayingMovies(language = language)
                    .collectLatest {
                        nowPlayingAdapter.submitData(it)
                    }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getLanguage().collectLatest {
                val genreList = viewModel.getMovieGenreList(it).genres
                nowPlayingAdapter.passMovieGenreList(genreList)
                popularMoviesRecyclerView.passMovieGenreList(genreList)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getLanguage().collectLatest {
                viewModel.getPopularMovies(language = it).collectLatest { pagingData ->
                    popularMoviesRecyclerView.submitData(pagingData)
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