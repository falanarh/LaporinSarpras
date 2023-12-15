package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.ui.theme.LaporinSarprasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaScreen(navController: NavController){
    val currentScreen= mutableStateOf<Screen>(Screen.Beranda)

    Scaffold (
        topBar = {},
        bottomBar = {
            CustomBottomNavigation(navController = navController, currentScreenId = Screen.Beranda.id){
                currentScreen.value = it
                navController.navigate(it.id)
            }
        },
    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
        }
    }
}

@Preview
@Composable
fun BerandaScreenPreview() {
    LaporinSarprasTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            BerandaScreen(navController)
        }
    }
}