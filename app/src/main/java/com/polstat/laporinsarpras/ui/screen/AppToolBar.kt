package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.theme.Roboto

@Composable
fun AppToolbar(
    toolbarTitle: String,
    navigationIconClicked: () -> Unit
) {

    TopAppBar(
        backgroundColor = Green,
        title = {
            Text(
                text = toolbarTitle,
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navigationIconClicked.invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back",
                    tint = Color.White
                )
            }

        },
        actions = {}
    )
}


@Preview
@Composable
fun SimpleTopAppBarPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
//        SimpleTopAppBar(title = "Pengaduan Mendesak")
        AppToolbar(toolbarTitle = "Pengaduan Mendesak") {}
    }
}