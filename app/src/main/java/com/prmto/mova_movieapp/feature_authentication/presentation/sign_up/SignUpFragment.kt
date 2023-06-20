package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.core.presentation.util.addOnBackPressedCallback
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.databinding.FragmentSignUpBinding
import com.prmto.mova_movieapp.feature_authentication.presentation.util.AuthUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSignUpBinding.bind(view)
        _binding = binding

        collectData()

        addOnBackPressedCallback(
            activity = requireActivity(),
            onBackPressed = {
                viewModel.onEvent(SignUpEvent.OnBackPressed)
            }
        )

        binding.edtEmail.addTextChangedListener {
            it?.let {
                viewModel.onEvent(SignUpEvent.EnteredEmail(it.toString()))
            }
        }

        binding.edtPassword.addTextChangedListener {
            it?.let { viewModel.onEvent(SignUpEvent.EnteredPassword(it.toString())) }
        }

        binding.btnSignUp.setOnClickListener {
            viewModel.onEvent(SignUpEvent.SignUp)
        }

        binding.txtSignIn.setOnClickListener {
            viewModel.onEvent(SignUpEvent.ClickedSignIn)
        }
    }

    private fun collectData() {
        collectSignUpUiState()
        collectEmailState()
        collectPasswordState()
        collectLoadingState()
    }

    private fun collectEmailState() {
        collectFlow(viewModel.emailState) { emailState ->
            AuthUtil.updateFieldEmptyErrorInTextInputLayout(
                textInputLayout = binding.layoutEmail,
                authError = emailState.error,
                context = requireContext()
            )
        }
    }

    private fun collectLoadingState() {
        collectFlow(viewModel.isLoading) { isLoading ->
            binding.edtEmail.isEnabled = !isLoading
            binding.edtPassword.isEnabled = !isLoading
            binding.btnSignUp.isEnabled = !isLoading
            binding.progressBar.isVisible = isLoading

        }
    }

    private fun collectPasswordState() {
        collectFlow(viewModel.passwordState) { passwordState ->
            AuthUtil.updateFieldEmptyErrorInTextInputLayout(
                textInputLayout = binding.layoutPassword,
                authError = passwordState.error,
                context = requireContext()
            )
        }
    }

    private fun collectSignUpUiState() {
        collectFlow(viewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                is UiEvent.NavigateTo -> {
                    findNavController().navigate(uiEvent.directions)
                }

                is UiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                }

                is UiEvent.ShowSnackbar -> {
                    Snackbar.make(
                        requireView(),
                        uiEvent.uiText.asString(requireContext()),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}