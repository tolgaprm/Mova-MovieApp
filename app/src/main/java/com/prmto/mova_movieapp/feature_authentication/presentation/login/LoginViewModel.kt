package com.prmto.mova_movieapp.feature_authentication.presentation.login

import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.prmto.core_domain.util.UiText
import com.prmto.mova_movieapp.core.presentation.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.FirebaseUseCases
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.SignInWithCredentialUseCase
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.SignInWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
                addConsumableViewEvent(UiEvent.NavigateTo(directions))
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
        val loginResult = signInWithEmailAndUserName(
            email = email,
            password = password,
            onSuccess = { onLoginSuccess() },
            onFailure = { uiText ->
                addConsumableViewEvent(UiEvent.ShowSnackbar(uiText = uiText))
                mutableState.updateIsLoading(false)
            }
        )

        if (loginResult.passwordError == null && loginResult.emailError == null) {
            mutableState.updateIsLoading(true)
        }

        if (loginResult.emailError != null) {
            mutableState.updateEmailError(loginResult.emailError)
        }
        if (loginResult.passwordError != null) {
            mutableState.updatePasswordError(loginResult.passwordError)
        }
    }

    private fun handleResultsForSignInWithGoogle(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            mutableState.updateIsLoading(true)
            val result = signInWithCredentialUseCase(
                task = task,
                onSuccess = { onLoginSuccess() },
                onFailure = { uiText ->
                    addConsumableViewEvent(UiEvent.ShowSnackbar(uiText = uiText))
                    mutableState.updateIsLoading(false)
                }
            )
            if (result.errorMessage != null) {
                addConsumableViewEvent(UiEvent.ShowSnackbar(result.errorMessage))
                mutableState.updateIsLoading(false)
            }
        } else {
            addConsumableViewEvent(UiEvent.ShowSnackbar(UiText.somethingWentWrong()))
        }
    }

    private fun onLoginSuccess() {
        val movieJob = getMoviesFromFirebaseThenUpdateLocalDatabase()
        val tvSeriesJob = getTvSeriesFromFirebaseThenUpdateLocalDatabase()
        viewModelScope.launch {
            movieJob.join()
            tvSeriesJob.join()
            addConsumableViewEvent(
                UiEvent.NavigateTo(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            )
            mutableState.updateIsLoading(false)
        }
    }

    private fun getMoviesFromFirebaseThenUpdateLocalDatabase(): Job {
        return viewModelScope.launch {
            firebaseUseCases.getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )

            firebaseUseCases.getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )
            delay(5000)
        }
    }

    private fun getTvSeriesFromFirebaseThenUpdateLocalDatabase(): Job {
        return viewModelScope.launch {
            firebaseUseCases.getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )
            firebaseUseCases.getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )
            delay(5000)
        }
    }
}