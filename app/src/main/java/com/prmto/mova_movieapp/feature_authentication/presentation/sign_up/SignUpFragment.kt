package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.authentication_ui.databinding.FragmentSignUpBinding
import com.prmto.authentication_ui.signUp.SignUpEvent
import com.prmto.authentication_ui.util.updateFieldEmptyError
import com.prmto.core_ui.base.fragment.BaseFragmentWithUiEvent
import com.prmto.core_ui.util.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragmentWithUiEvent<FragmentSignUpBinding, SignUpViewModel>(
    inflater = FragmentSignUpBinding::inflate
) {

    override val viewModel: SignUpViewModel by viewModels()
    override fun onInitialize() {
        collectData()
        addOnBackPressedCallback()
        setClickListeners()
        addTextChangeListeners()
    }

    private fun collectData() {
        collectUiEvent()
        collectUiState()
    }

    private fun setClickListeners() {
        binding.btnSignUp.setOnClickListener {
            viewModel.onEvent(SignUpEvent.SignUp)
        }

        binding.txtSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addTextChangeListeners() {
        binding.edtEmail.addTextChangedListener {
            it?.let {
                viewModel.onEvent(SignUpEvent.EnteredEmail(it.toString()))
            }
        }
        binding.edtPassword.addTextChangedListener {
            it?.let { viewModel.onEvent(SignUpEvent.EnteredPassword(it.toString())) }
        }
    }

    private fun collectUiState() {
        collectFlow(viewModel.uiState) { uiState ->
            binding.layoutEmail.updateFieldEmptyError(
                authError = uiState.emailState.error
            )

            binding.layoutPassword.updateFieldEmptyError(
                authError = uiState.passwordState.error
            )

            binding.edtEmail.isEnabled = !uiState.isLoading
            binding.edtPassword.isEnabled = !uiState.isLoading
            binding.btnSignUp.isEnabled = !uiState.isLoading
            binding.progressBar.isVisible = uiState.isLoading
        }
    }

    private fun collectUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { listOfUiEvent ->
            handleUiEvent(
                listOfUiEvent = listOfUiEvent,
                onEventConsumed = viewModel::onEventConsumed
            )
        }
    }
}