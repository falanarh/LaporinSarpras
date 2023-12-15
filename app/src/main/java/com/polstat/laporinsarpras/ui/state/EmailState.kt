package com.polstat.laporinsarpras.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData

// Kelas untuk state management
class EmailState {
    var email by mutableStateOf("")
    var isEmailValid by mutableStateOf(true)
    var isEmailFocused by mutableStateOf(false)
    var emailErrorMessage by mutableStateOf("")

    fun validateEmail() {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        isEmailValid = email.matches(emailPattern.toRegex())
        emailErrorMessage = if (isEmailValid) "" else "Email tidak valid!"
    }
}
