package com.prmto.authentication_ui.login

import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.prmto.authentication_domain.use_case.FirebaseUseCases
import com.prmto.authentication_domain.use_case.SignInWithCredentialUseCase
import com.prmto.authentication_domain.use_case.SignInWithEmailAndPasswordUseCase
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
class LoginViewModel @Inject constructor(
    private val signInWithEmailAndUserName: SignInWithEmailAndPasswordUseCase,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val firebaseUseCases: FirebaseUseCases
) : BaseViewModelWithUiEvent<UiEvent>() {

    private val mutableState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = mutableState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                mutableState.updateEmailText(event.email)
            }

            is LoginEvent.EnteredPassword -> {
                mutableState.updatePasswordText(event.password)
            }

            is LoginEvent.ClickedForgetPassword -> {
                val directions =
                    LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment(null)
                if (mutableState.value.emailIsNotBlank()) {
                    directions.email = mutableState.value.emailState.text
                }
                addConsumableViewEvent(UiEvent.NavigateToDirections(directions))
            }

            is LoginEvent.SignIn -> {
                signIn(
                    email = mutableState.value.emailState.text,
                    password = mutableState.value.passwordState.text
                )
            }

            is LoginEvent.SignInWithGoogle -> {
                handleResultsForSignInWithGoogle(task = event.task)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val loginResult = signInWithEmailAndUserName(
                email = email,
                password = password
            )

            if (loginResult.passwordError == null && loginResult.emailError == null) {
                mutableState.updateIsLoading(true)
            }

            loginResult.emailError?.let { emailError ->
                mutableState.updateEmailError(emailError)
            }

            loginResult.passwordError?.let { passwordError ->
                mutableState.updatePasswordError(passwordError)
            }

            when (val resource = loginResult.result) {
                is Resource.Error -> {
                    addConsumableViewEvent(
                        UiEvent.ShowSnackbar(
                            uiText = resource.uiText ?: UiText.unknownError()
                        )
                    )
                    mutableState.updateIsLoading(false)
                }

                is Resource.Success -> onLoginSuccess()
                null -> return@launch
            }
        }
    }

    private fun handleResultsForSignInWithGoogle(task: Task<GoogleSignInAccount>) {
        viewModelScope.launch {
            if (task.isSuccessful) {
                mutableState.updateIsLoading(true)
                val result = signInWithCredentialUseCase(
                    task = task
                )
                result.errorMessage?.let { errorMessage ->
                    addConsumableViewEvent(UiEvent.ShowSnackbar(errorMessage))
                    mutableState.updateIsLoading(false)
                }

                when (val resource = result.result) {
                    is Resource.Error -> {
                        addConsumableViewEvent(
                            UiEvent.ShowSnackbar(
                                uiText = resource.uiText ?: UiText.unknownError()
                            )
                        )
                        mutableState.updateIsLoading(false)
                    }

                    is Resource.Success -> onLoginSuccess()
                    null -> return@launch
                }
            } else {
                addConsumableViewEvent(UiEvent.ShowSnackbar(UiText.somethingWentWrong()))
            }
        }
    }

    private fun onLoginSuccess() {
        viewModelScope.launch {
            firebaseUseCases.getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase()
            firebaseUseCases.getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase()
            firebaseUseCases.getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase()
            firebaseUseCases.getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase()
            addConsumableViewEvent(
                UiEvent.NavigateToFlow(NavigateFlow.HomeFlow)
            )
            mutableState.updateIsLoading(false)
        }
    }
}