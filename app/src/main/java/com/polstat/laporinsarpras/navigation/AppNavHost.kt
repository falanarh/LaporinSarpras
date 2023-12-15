package com.polstat.laporinsarpras.navigation

import LoginViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.api.ConfigurationApi
import com.polstat.laporinsarpras.ui.screen.LoginScreen
import com.polstat.laporinsarpras.ui.screen.LoginSuccessScreen
import com.polstat.laporinsarpras.ui.screen.LoginFailedScreen

@Composable
fun AppNavHost() {
    val configurationApi = ConfigurationApi()
    val loginViewModel = LoginViewModel(configurationApi.apiService)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(loginViewModel, navController) }
        composable("success") { LoginSuccessScreen(loginViewModel, navController) }
        composable("failed") { LoginFailedScreen(loginViewModel) }
    }
}
