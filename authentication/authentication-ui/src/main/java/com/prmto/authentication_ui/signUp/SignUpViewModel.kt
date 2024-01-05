package com.prmto.authentication_ui.signUp

import com.prmto.authentication_domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.core_ui.util.UiEvent
import com.prmto.navigation.NavigateFlow
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
                    UiEvent.NavigateToFlow(NavigateFlow.HomeFlow)
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

        result.emailError?.let {
            mutableState.updateEmailError(it)
        }

        result.passwordError?.let {
            mutableState.updatePasswordError(it)
        }
    }
}