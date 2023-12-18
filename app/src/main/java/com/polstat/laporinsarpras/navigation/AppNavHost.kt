package com.polstat.laporinsarpras.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.ui.screen.AsetScreen
import com.polstat.laporinsarpras.ui.screen.BerandaScreen
import com.polstat.laporinsarpras.ui.screen.LoginScreen
import com.polstat.laporinsarpras.ui.screen.LoginSuccessScreen
import com.polstat.laporinsarpras.ui.screen.LoginFailedScreen
import com.polstat.laporinsarpras.ui.screen.PengaduanScreen
import com.polstat.laporinsarpras.ui.screen.PengaduanMendesakScreen
import com.polstat.laporinsarpras.ui.screen.ProfilScreen
import com.polstat.laporinsarpras.ui.viewmodel.LoginViewModel
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakViewModel
import com.polstat.laporinsarpras.ui.viewmodel.ProfilViewModel

@Composable
fun AppNavHost() {
//    val loginViewModel = LoginViewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(viewModel(factory = LoginViewModel.Factory), navController) }
        composable("beranda") { BerandaScreen(navController)}
        composable("pengaduanMendesak") {
            PengaduanMendesakScreen(
                navController = navController,
                pengaduanMendesakViewModel = viewModel(factory = PengaduanMendesakViewModel.Factory)
            )
        }
        composable("pengaduan") { PengaduanScreen(navController) }
        composable("aset") { AsetScreen(navController) }
        composable("profil") {
            ProfilScreen(
                navController = navController,
                viewModel = viewModel(factory = ProfilViewModel.Factory)
            )
        }
//        composable("success") { LoginSuccessScreen(loginViewModel, navController) }
//        composable("failed") { LoginFailedScreen(loginViewModel) }
    }
}
