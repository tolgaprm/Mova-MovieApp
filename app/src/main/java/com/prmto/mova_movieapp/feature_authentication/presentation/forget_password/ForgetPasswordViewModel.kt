package com.prmto.mova_movieapp.feature_authentication.presentation.forget_password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.StandardTextFieldState
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.SendPasswordResetEmailUseCase
import com.prmto.mova_movieapp.feature_authentication.presentation.util.AuthUtil.Companion.EMAIL_ARGUMENT_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase
) : ViewModel() {

    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState: StateFlow<StandardTextFieldState> = _emailState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    init {
        val email = savedStateHandle.get<String>(EMAIL_ARGUMENT_NAME) ?: ""
        _emailState.update { it.copy(text = email) }
    }

    fun onEvent(event: ForgetEvent) {
        when (event) {
            is ForgetEvent.ClickedForgetPassword -> {
                sendPasswordResetToEmail(email = emailState.value.text)
            }
            is ForgetEvent.ClickedBackToLogin -> {
                emitUiEvent(UiEvent.PopBackStack)
            }
            is ForgetEvent.EnteredEmail -> {
                _emailState.update { it.copy(text = event.email) }
            }
        }
    }

    private fun emitUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(uiEvent)
        }
    }

    private fun sendPasswordResetToEmail(email: String) {
        val result = sendPasswordResetEmailUseCase(
            email = email,
            onSuccess = {
                emitUiEvent(UiEvent.ShowSnackbar(uiText = UiText.StringResource(R.string.check_your_email)))
            },
            onFailure = { uiText ->
                emitUiEvent(UiEvent.ShowSnackbar(uiText = uiText))
            },
        )

        if (result.emailError != null) {
            _emailState.update { it.copy(error = result.emailError) }
        }
    }
}