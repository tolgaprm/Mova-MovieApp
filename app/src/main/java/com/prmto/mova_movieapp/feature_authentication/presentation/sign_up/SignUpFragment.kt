package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.databinding.FragmentSignUpBinding
import com.prmto.mova_movieapp.feature_authentication.presentation.util.AuthUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { collectSignUpUiState() }
                launch { collectEmailState() }
                launch { collectPasswordState() }
                launch { collectLoadingState() }
            }
        }
    }

    private suspend fun collectEmailState() {
        viewModel.emailState.collectLatest { emailState ->
            AuthUtil.updateFieldEmptyErrorInTextInputLayout(
                textInputLayout = binding.layoutEmail,
                authError = emailState.error,
                context = requireContext()
            )
        }
    }

    private suspend fun collectLoadingState() {
        viewModel.isLoading.collectLatest { isLoading ->
            binding.edtEmail.isEnabled = !isLoading
            binding.edtPassword.isEnabled = !isLoading
            binding.btnSignUp.isEnabled = !isLoading
            binding.btnSignInFacebook.isEnabled = !isLoading
            binding.btnSignInGoogle.isEnabled = !isLoading
            binding.progressBar.isVisible = isLoading
        }
    }

    private suspend fun collectPasswordState() {
        viewModel.passwordState.collectLatest { passwordState ->
            AuthUtil.updateFieldEmptyErrorInTextInputLayout(
                textInputLayout = binding.layoutPassword,
                authError = passwordState.error,
                context = requireContext()
            )
        }
    }

    private suspend fun collectSignUpUiState() {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is SignUpUiEvent.NavigateTo -> {
                    findNavController().navigate(uiEvent.directions)
                }
                is SignUpUiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                }
                is SignUpUiEvent.ShowSnackbar -> {
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