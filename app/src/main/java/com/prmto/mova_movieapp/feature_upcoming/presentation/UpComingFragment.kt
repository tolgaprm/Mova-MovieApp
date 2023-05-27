package com.prmto.mova_movieapp.feature_upcoming.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentUpComingBinding


class UpComingFragment : Fragment(R.layout.fragment_up_coming) {


    private var _binding: FragmentUpComingBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUpComingBinding.bind(view)
        _binding = binding

    }

}