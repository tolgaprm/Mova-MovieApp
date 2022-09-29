package com.prmto.mova_movieapp.presentation.myList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentMyListBinding

class MyListFragment : Fragment(R.layout.fragment_my_list) {

    private var binding: FragmentMyListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val binding = FragmentMyListBinding.bind(view)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}