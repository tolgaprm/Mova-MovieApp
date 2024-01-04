package com.prmto.mova_movieapp.feature_authentication.presentation.forget_password

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.authentication_ui.databinding.FragmentForgetPasswordBinding
import com.prmto.authentication_ui.forgetPassword.ForgetEvent
import com.prmto.authentication_ui.util.updateFieldEmptyError
import com.prmto.core_ui.base.fragment.BaseFragmentWithUiEvent
import com.prmto.core_ui.util.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment :
    BaseFragmentWithUiEvent<FragmentForgetPasswordBinding, ForgetPasswordViewModel>(
        inflater = FragmentForgetPasswordBinding::inflate
    ) {

    override val viewModel: ForgetPasswordViewModel by viewModels()
    override fun onInitialize() {
        collectData()
        setClickListeners()
        binding.edtEmail.addTextChangedListener {
            it?.let {
                viewModel.onEvent(ForgetEvent.EnteredEmail(it.toString()))
            }
        }
    }

    private fun setClickListeners() {
        binding.txtBackToLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnForgetPassword.setOnClickListener {
            viewModel.onEvent(ForgetEvent.ClickedForgetPassword)
        }
    }

    private fun collectData() {
        collectForgetPasswordUiEvent()
        collectUiState()
    }

    private fun collectUiState() {
        collectFlow(viewModel.uiState) { uiState ->
            binding.layoutEmail.updateFieldEmptyError(
                authError = uiState.emailState.error
            )
        }
    }

    private fun collectForgetPasswordUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { listOfUiEvents ->
            handleUiEvent(
                listOfUiEvent = listOfUiEvents,
                onEventConsumed = viewModel::onEventConsumed
            )
        }
    }
}