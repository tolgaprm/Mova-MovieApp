package com.prmto.mova_movieapp.presentation.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.prmto.mova_movieapp.presentation.home.HomeFragment
import com.prmto.mova_movieapp.presentation.home.recyler.NowPlayingRecyclerAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.PopularMoviesAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.PopularTvSeriesAdapter
import com.prmto.mova_movieapp.presentation.home.recyler.TopRatedMoviesAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter,
    private val popularMoviesAdapter: PopularMoviesAdapter,
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter,
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(
                nowPlayingAdapter = nowPlayingRecyclerAdapter,
                popularMoviesAdapter = popularMoviesAdapter,
                topRatedMoviesAdapter = topRatedMoviesAdapter,
                popularTvSeriesAdapter = popularTvSeriesAdapter
            )

            else -> super.instantiate(classLoader, className)
        }
    }
}