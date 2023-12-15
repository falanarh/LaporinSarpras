package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.polstat.laporinsarpras.ui.theme.LaporinSarprasTheme

@Composable
fun BerandaScreen(){

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
            BerandaScreen()
        }
    }
}