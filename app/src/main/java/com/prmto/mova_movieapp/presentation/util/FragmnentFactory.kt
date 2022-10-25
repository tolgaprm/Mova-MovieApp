package com.prmto.mova_movieapp.presentation.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.prmto.mova_movieapp.presentation.detail_bottom_sheet.DetailBottomSheet
import com.prmto.mova_movieapp.presentation.explore.ExploreFragment
import com.prmto.mova_movieapp.presentation.filter_bottom_sheet.FilterBottomSheetFragment
import com.prmto.mova_movieapp.presentation.home.HomeFragment
import com.prmto.mova_movieapp.presentation.home.recyler.*
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter,
    private val popularMoviesAdapter: PopularMoviesAdapter,
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter,
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter,
    private val topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(
                nowPlayingAdapter = nowPlayingRecyclerAdapter,
                popularMoviesAdapter = popularMoviesAdapter,
                topRatedMoviesAdapter = topRatedMoviesAdapter,
                popularTvSeriesAdapter = popularTvSeriesAdapter,
                topRatedTvSeriesAdapter = topRatedTvSeriesAdapter
            )
            ExploreFragment::class.java.name -> ExploreFragment()
            DetailBottomSheet::class.java.name -> DetailBottomSheet()
            FilterBottomSheetFragment::class.java.name -> FilterBottomSheetFragment()

            else -> super.instantiate(classLoader, className)
        }
    }
}