package com.prmto.mova_movieapp.feature_authentication.presentation.forget_password

sealed class ForgetEvent {
    data class EnteredEmail(val email: String) : ForgetEvent()
    object ClickedForgetPassword : ForgetEvent()
    object ClickedBackToLogin : ForgetEvent()
}
