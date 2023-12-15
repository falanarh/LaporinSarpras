package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsetScreen(navController: NavController){
    val currentScreen= mutableStateOf<Screen>(Screen.Beranda)

    Scaffold (
        topBar = {},
        bottomBar = {
            CustomBottomNavigation(navController = navController, currentScreenId = Screen.Aset.id){
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
