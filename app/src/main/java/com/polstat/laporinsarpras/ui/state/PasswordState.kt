package com.polstat.laporinsarpras.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData

// Kelas untuk state management
class PasswordState {
    var password by mutableStateOf("")
    var isPasswordValid by mutableStateOf(true)
    var isPasswordFocused by mutableStateOf(false)
    var passwordErrorMessage by mutableStateOf("")

    fun validatePassword() {
        isPasswordValid = password.length >= 8
        passwordErrorMessage = if (isPasswordValid) "" else "Password harus memiliki minimal 8 karakter"
    }
}