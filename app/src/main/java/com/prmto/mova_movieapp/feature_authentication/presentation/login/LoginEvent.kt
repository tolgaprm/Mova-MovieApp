package com.prmto.mova_movieapp.feature_authentication.presentation.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

sealed class LoginEvent {
    data class EnteredEmail(val email: String) : LoginEvent()
    data class EnteredPassword(val password: String) : LoginEvent()
    object ClickedForgetPassword : LoginEvent()
    data class SignInWithGoogle(val task: Task<GoogleSignInAccount>) : LoginEvent()
    object SignIn : LoginEvent()
}
