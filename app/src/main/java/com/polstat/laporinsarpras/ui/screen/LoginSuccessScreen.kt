package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polstat.laporinsarpras.ui.theme.Roboto
import com.polstat.laporinsarpras.ui.viewmodel.LoginViewModel


@Composable
fun LoginSuccessScreen(loginViewModel: LoginViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login berhasil!" + loginViewModel.loginResponse.value?.data?.email,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Green
        )
        Button(
            onClick = {
                navController.navigate("login")
            }
        ) {
            Text(
                text = "Logout",
                fontFamily = Roboto,
                fontSize = 12.sp
            )
        }
    }
}