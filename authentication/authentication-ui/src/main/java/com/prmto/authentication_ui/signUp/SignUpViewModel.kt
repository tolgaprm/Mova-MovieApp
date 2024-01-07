package com.prmto.authentication_ui.signUp

import androidx.lifecycle.viewModelScope
import com.prmto.authentication_domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.core_ui.util.UiEvent
import com.prmto.navigation.NavigateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            val result = createUserWithEmailAndPasswordUseCase(
                email = mutableState.value.emailState.text,
                password = mutableState.value.passwordState.text
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

            when (result.result) {
                is Resource.Error -> {
                    addConsumableViewEvent(
                        UiEvent.ShowSnackbar(
                            uiText = result.result?.uiText ?: UiText.unknownError()
                        )
                    )
                    mutableState.updateIsLoading(false)
                }

                is Resource.Success -> {
                    addConsumableViewEvent(
                        UiEvent.NavigateToFlow(NavigateFlow.HomeFlow)
                    )
                    mutableState.updateIsLoading(false)
                }

                null -> return@launch
            }
        }
    }
}