package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

import com.prmto.mova_movieapp.core.presentation.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
) : BaseViewModelWithUiEvent<UiEvent>() {

    private val mutableState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = mutableState.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EnteredEmail -> {
                mutableState.updateEmailText(event.email)
            }

            is SignUpEvent.EnteredPassword -> {
                mutableState.updatePasswordText(event.password)
            }

            is SignUpEvent.SignUp -> {
                createUser()
            }
        }
    }

    private fun createUser(
    ) {
        val result = createUserWithEmailAndPasswordUseCase(
            email = mutableState.value.emailState.text,
            password = mutableState.value.passwordState.text,
            onSuccess = {
                addConsumableViewEvent(
                    UiEvent.NavigateTo(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                )
                mutableState.updateIsLoading(false)
            },
            onFailure = { uiText ->
                addConsumableViewEvent(UiEvent.ShowSnackbar(uiText = uiText))
                mutableState.updateIsLoading(false)
            }
        )

        if (result.passwordError == null && result.emailError == null) {
            mutableState.updateIsLoading(true)
        }

        if (result.emailError != null) {
            mutableState.updateEmailError(result.emailError)
        }

        if (result.passwordError != null) {
            mutableState.updatePasswordError(result.passwordError)
        }
    }
}