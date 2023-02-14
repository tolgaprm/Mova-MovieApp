package com.prmto.mova_movieapp.feature_authentication.presentation.login

sealed class LoginEvent {
    data class EnteredEmail(val email: String) : LoginEvent()
    data class EnteredPassword(val password: String) : LoginEvent()
    object ClickedForgetPassword : LoginEvent()
    object SignInWithGoogle : LoginEvent()
    object SignInWithFacebook : LoginEvent()
    object SignIn : LoginEvent()
    object ClickedSignUp : LoginEvent()
    object OnBackPressed : LoginEvent()
}
