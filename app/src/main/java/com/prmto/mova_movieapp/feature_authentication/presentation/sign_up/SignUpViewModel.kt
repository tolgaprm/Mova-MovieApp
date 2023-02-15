package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.core.presentation.util.StandardTextFieldState
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
) : ViewModel() {
    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState: StateFlow<StandardTextFieldState> = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(StandardTextFieldState())
    val passwordState: StateFlow<StandardTextFieldState> = _passwordState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EnteredEmail -> {
                _emailState.update { it.copy(text = event.email, error = null) }
            }
            is SignUpEvent.EnteredPassword -> {
                _passwordState.update { it.copy(text = event.password, error = null) }
            }
            is SignUpEvent.SignUp -> {
                createUser(email = emailState.value.text, password = passwordState.value.text)
            }
            is SignUpEvent.ClickedSignIn -> {
                emitUiEvent(UiEvent.PopBackStack)
            }
            is SignUpEvent.OnBackPressed -> {
                emitUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun emitUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(uiEvent)
        }
    }

    private fun createUser(
        email: String,
        password: String
    ) {
        val result = createUserWithEmailAndPasswordUseCase(
            email = email,
            password = password,
            onSuccess = {
                emitUiEvent(UiEvent.NavigateTo(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()))
                _isLoading.value = false
            },
            onFailure = { uiText ->
                emitUiEvent(UiEvent.ShowSnackbar(uiText = uiText))
                _isLoading.value = false
            }
        )

        if (result.passwordError == null && result.emailError == null) {
            _isLoading.value = true
        }

        if (result.emailError != null) {
            _emailState.update { it.copy(error = result.emailError) }
        }

        if (result.passwordError != null) {
            _passwordState.update { it.copy(error = result.passwordError) }
        }
    }
}