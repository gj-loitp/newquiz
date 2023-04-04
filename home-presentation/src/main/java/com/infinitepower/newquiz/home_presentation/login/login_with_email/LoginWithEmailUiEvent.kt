package com.infinitepower.newquiz.home_presentation.login.login_with_email

interface LoginWithEmailUiEvent {
    data class EmailChanged(val email: String) : LoginWithEmailUiEvent

    data class PasswordChanged(val password: String) : LoginWithEmailUiEvent

    data class NameChanged(val name: String) : LoginWithEmailUiEvent

    object VerifyEmailClick : LoginWithEmailUiEvent

    object LoginButtonClicked : LoginWithEmailUiEvent
}