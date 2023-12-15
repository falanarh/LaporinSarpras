package com.polstat.laporinsarpras.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.theme.Roboto

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomBottomNavigation(
    navController: NavController,
    currentScreenId: String,
    onItemSelected: (Screen) -> Unit,
) {
    val items = Screen.Items.list

    Surface(
        elevation = 150.dp
    ) {
        Row(
            modifier = Modifier
                .background(color = Green)
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->
                CustomBottomNavigationItem(item = item, isSelected = item.id == currentScreenId) {
                    navController.navigate(item.id)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun CustomBottomNavigationItem(item: Screen, isSelected: Boolean, onClick: () -> Unit) {
    val background =
        if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Transparent
    val contentColor =
        if (isSelected) Green else Color.White

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor
            )

            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = item.title,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Medium,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
@Preview
fun Prev1() {
    val navController = rememberNavController()
    CustomBottomNavigation(navController = navController, currentScreenId = Screen.Beranda.id) {}
}

@ExperimentalAnimationApi
@Composable
@Preview
fun Prev2() {
    CustomBottomNavigationItem(item = Screen.Beranda, isSelected = true) {}
}