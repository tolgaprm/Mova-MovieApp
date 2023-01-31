package com.prmto.mova_movieapp.feature_authentication.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        _binding = binding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}