package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

sealed class SignUpEvent {
    data class EnteredEmail(val email: String) : SignUpEvent()
    data class EnteredPassword(val password: String) : SignUpEvent()
    object SignUpWithGoogle : SignUpEvent()
    object SignUpWithFacebook : SignUpEvent()
    object SignUp : SignUpEvent()
    object ClickedSignIn : SignUpEvent()
}
