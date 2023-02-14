package com.prmto.mova_movieapp.feature_authentication.presentation.forget_password

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.databinding.FragmentForgetPasswordBinding
import com.prmto.mova_movieapp.feature_authentication.presentation.util.AuthUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment(R.layout.fragment_forget_password) {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentForgetPasswordBinding.bind(view)
        _binding = binding

        collectData()



        binding.edtEmail.addTextChangedListener {
            it?.let {
                viewModel.onEvent(ForgetEvent.EnteredEmail(it.toString()))
            }
        }

        binding.txtBackToLogin.setOnClickListener {
            viewModel.onEvent(ForgetEvent.ClickedBackToLogin)
        }

        binding.btnForgetPassword.setOnClickListener {
            viewModel.onEvent(ForgetEvent.ClickedForgetPassword)
        }
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { collectForgetPasswordUiEvent() }

                launch { collectEmailState() }
            }
        }
    }

    private suspend fun collectEmailState() {
        viewModel.emailState.collectLatest { emailState ->
            AuthUtil.updateFieldEmptyErrorInTextInputLayout(
                textInputLayout = binding.layoutEmail,
                context = requireContext(),
                authError = emailState.error
            )
        }
    }

    private suspend fun collectForgetPasswordUiEvent() {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                }
                is UiEvent.ShowSnackbar -> {
                    Snackbar.make(
                        requireView(),
                        event.uiText.asString(requireContext()),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is UiEvent.NavigateTo -> {
                    findNavController().navigate(event.directions)
                }
            }
        }
    }

}