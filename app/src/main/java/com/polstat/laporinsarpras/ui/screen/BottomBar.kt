package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.ui.theme.LaporinSarprasTheme

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MeowBottomNavigationSample() {
//    val navController = rememberNavController()
//    val items = listOf(Screen.Home, Screen.Message, Screen.Profile)
//    Scaffold(
//        bottomBar = {
//            BottomNavigation {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentRoute = navBackStackEntry?.arguments?.getString("KEY_ROUTE")
//                items.forEach { screen ->
//                    BottomNavigationItem(
//                        icon = { Icon(screen.icon, contentDescription = null) },
//                        label = { Text(screen.label) },
//                        selected = currentRoute == screen.route,
//                        onClick = {
//                            navController.navigate(screen.route) {
//                                popUpTo = navController.graph.startDestinationId
//                                launchSingleTop = true
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
//        Navigation(navController = navController, modifier = Modifier.padding(innerPadding))
//    }
//}
//
//@Composable
//fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
//    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
//        composable(Screen.Home.route) { }
//        composable(Screen.Message.route) { }
//        composable(Screen.Profile.route) { }
//    }
//}
//
//sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
//    object Home : Screen("home", "Home", Icons.Default.Home)
//    object Message : Screen("message", "Message", Icons.Default.Email)
//    object Profile : Screen("profile", "Profile", Icons.Default.AccountBox)
//}


//@Preview(showBackground = true)
//@Composable
//fun PreviewMeowBottomNavigation() {
//    LaporinSarprasTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            MeowBottomNavigationSample()
//        }
//    }
//}
