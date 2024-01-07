package com.prmto.authentication_ui.forgetPassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prmto.authentication_domain.use_case.SendPasswordResetEmailUseCase
import com.prmto.authentication_ui.R
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.core_ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase
) : BaseViewModelWithUiEvent<UiEvent>() {

    private val mutableState = MutableStateFlow(ForgetPasswordUiState())
    val uiState: StateFlow<ForgetPasswordUiState> = mutableState.asStateFlow()

    init {
        ForgetPasswordFragmentArgs.fromSavedStateHandle(savedStateHandle).email?.let { email ->
            mutableState.update {
                it.copy(
                    emailState = it.updateEmailText(email)
                )
            }
        }
    }

    fun onEvent(event: ForgetEvent) {
        when (event) {
            is ForgetEvent.ClickedForgetPassword -> {
                sendPasswordResetToEmail(email = uiState.value.emailState.text)
            }

            is ForgetEvent.EnteredEmail -> {
                mutableState.update {
                    it.copy(
                        emailState = it.emailState.copy(
                            text = event.email,
                            error = null
                        )
                    )
                }
            }
        }
    }

    private fun sendPasswordResetToEmail(email: String) {
        viewModelScope.launch {
            val result = sendPasswordResetEmailUseCase(
                email = email
            )

            result.emailError?.let { emailError ->
                mutableState.update {
                    it.copy(
                        emailState = it.updateEmailError(emailError)
                    )
                }
            }

            when (val resource = result.result) {
                is Resource.Error -> {
                    addConsumableViewEvent(
                        UiEvent.ShowSnackbar(
                            uiText = resource.uiText ?: UiText.unknownError()
                        )
                    )
                }

                is Resource.Success -> {
                    addConsumableViewEvent(
                        UiEvent.ShowSnackbar(uiText = UiText.StringResource(R.string.check_your_email))
                    )
                }

                null -> return@launch
            }
        }
    }
}