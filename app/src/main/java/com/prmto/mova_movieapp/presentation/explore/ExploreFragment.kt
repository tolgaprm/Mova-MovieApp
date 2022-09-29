package com.prmto.mova_movieapp.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentExploreBinding

class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentExploreBinding.bind(view)
        val binding = _binding
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}