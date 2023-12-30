package com.prmto.mova_movieapp.feature_authentication.presentation.forget_password

import androidx.lifecycle.SavedStateHandle
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.util.UiText
import com.prmto.mova_movieapp.core.presentation.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.SendPasswordResetEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
        val result = sendPasswordResetEmailUseCase(
            email = email,
            onSuccess = {
                addConsumableViewEvent(
                    UiEvent.ShowSnackbar(uiText = UiText.StringResource(R.string.check_your_email))
                )
            },
            onFailure = { uiText ->
                addConsumableViewEvent(UiEvent.ShowSnackbar(uiText = uiText))
            },
        )

        if (result.emailError != null) {
            mutableState.update {
                it.copy(
                    emailState = it.updateEmailError(result.emailError)
                )
            }
        }
    }
}