package com.prmto.authentication_ui.forgetPassword

sealed class ForgetEvent {
    data class EnteredEmail(val email: String) : ForgetEvent()
    object ClickedForgetPassword : ForgetEvent()
}