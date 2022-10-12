package com.prmto.mova_movieapp.presentation.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.prmto.mova_movieapp.presentation.home.HomeFragment
import com.prmto.mova_movieapp.presentation.home.recyler.NowPlayingRecyclerAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(
                nowPlayingAdapter = nowPlayingRecyclerAdapter
            )

            else -> super.instantiate(classLoader, className)
        }
    }
}