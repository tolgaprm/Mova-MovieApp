package com.prmto.authentication_ui.signUp

sealed class SignUpEvent {
    data class EnteredEmail(val email: String) : SignUpEvent()
    data class EnteredPassword(val password: String) : SignUpEvent()
    object SignUp : SignUpEvent()
}
