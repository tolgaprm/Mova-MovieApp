package com.prmto.mova_movieapp.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment @Inject constructor(

) : Fragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null

    lateinit var viewModel: ExploreViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentExploreBinding.bind(view)
        val binding = _binding

        viewModel = ViewModelProvider(this)[ExploreViewModel::class.java]

        binding?.filter?.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToFilterBottomSheetFragment())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}