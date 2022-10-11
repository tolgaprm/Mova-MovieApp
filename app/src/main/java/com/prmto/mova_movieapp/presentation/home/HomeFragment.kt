package com.prmto.mova_movieapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.presentation.home.recyler.NowPlayingRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null


    private val viewModel: HomeViewModel by viewModels()

    private val nowPlayingAdapter by lazy { NowPlayingRecyclerAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nowPlayingMovies.collectLatest {
                    nowPlayingAdapter.submitData(it)
                }
            }


        }


        binding.nowPlayingRecyclerView.adapter = nowPlayingAdapter
        binding.nowPlayingRecyclerView.setAlpha(true)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}