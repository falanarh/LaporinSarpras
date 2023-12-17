package com.polstat.laporinsarpras.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.polstat.laporinsarpras.ui.viewmodel.LoginViewModel

@Composable
fun LoginFailedScreen(loginViewModel: LoginViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Login gagal. Message: " + loginViewModel.loginResponse.value?.message,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Red
        )
    }
}