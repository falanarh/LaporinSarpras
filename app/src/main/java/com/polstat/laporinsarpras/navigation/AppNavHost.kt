package com.polstat.laporinsarpras.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.ui.screen.AsetScreen
import com.polstat.laporinsarpras.ui.screen.BerandaScreen
import com.polstat.laporinsarpras.ui.screen.LoginScreen
import com.polstat.laporinsarpras.ui.screen.LoginSuccessScreen
import com.polstat.laporinsarpras.ui.screen.LoginFailedScreen
import com.polstat.laporinsarpras.ui.screen.NotifikasiScreen
import com.polstat.laporinsarpras.ui.screen.PengaduanScreen
import com.polstat.laporinsarpras.ui.screen.ProfilScreen
import com.polstat.laporinsarpras.ui.viewmodel.LoginViewModel

@Composable
fun AppNavHost() {
    val loginViewModel = LoginViewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(loginViewModel, navController) }
        composable("beranda") { BerandaScreen(navController)}
        composable("pengaduan") { PengaduanScreen(navController) }
        composable("notifikasi") { NotifikasiScreen(navController) }
        composable("aset") { AsetScreen(navController) }
        composable("profil") { ProfilScreen(navController)}
        composable("success") { LoginSuccessScreen(loginViewModel, navController) }
        composable("failed") { LoginFailedScreen(loginViewModel) }
    }
}
