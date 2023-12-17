package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.R
import com.polstat.laporinsarpras.repository.PengaduanRepository
import com.polstat.laporinsarpras.repository.UserPreferencesRepository
import com.polstat.laporinsarpras.repository.UserRepository
import com.polstat.laporinsarpras.ui.theme.DarkGreen
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.theme.LaporinSarprasTheme
import com.polstat.laporinsarpras.ui.theme.Roboto
import com.polstat.laporinsarpras.ui.theme.typography
import com.polstat.laporinsarpras.ui.viewmodel.BerandaViewModel
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakViewModel

enum class LaporinSarprasScreen {
    Login,
    Register,
    Beranda,
    Pengaduan,
    PengaduanMendesak,
    PengaduanTerbaru,
    Notifikasi,
    Aset,
    Profil
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaScreen(navController: NavController, berandaViewModel: BerandaViewModel = viewModel(factory = BerandaViewModel.Factory)) {
    val currentScreen = mutableStateOf<Screen>(Screen.Beranda)

    Scaffold(
        topBar = {},
        bottomBar = {
            CustomBottomNavigation(
                navController = navController,
                currentScreenId = Screen.Beranda.id
            ) {
                currentScreen.value = it
                navController.navigate(it.id)
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .height(250.dp)
                ) {
                    BerandaAtas()
                }
                Spacer(modifier = Modifier.height(10.dp))
                PengaduanSection(navController)
                Spacer(modifier = Modifier.height(20.dp))
                RekapitulasiSection()
                Spacer(modifier = Modifier.height(550.dp))
            }
        }
    }
}

@Composable
fun NamaDanFoto() {
//    val user = User(
//        email = "falana@gmail.com",
//        roles = listOf("ROLE_KOORDINATOR"),
//        position = "STAFF",
//        firstName = "Falana",
//        lastName = "Rofako",
//        phoneNumber = "085748103989",
//        address = "Jalan Ayub 16A/20"
//    )

    Row(
        modifier = Modifier
            .fillMaxWidth(0.85f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hai, Falana!",
                style = typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Kelola Sarpras Kelas Kita!",
                style = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                color = Color.White
            )
        }
        Image(
            painter = painterResource(R.drawable.profile_icon),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(80.dp)
        )
    }
}

@Composable
fun PengaduanCard() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(0.8f),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Pengaduan Dalam Proses",
                            style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                fontFamily = Roboto,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                            ),
                            color = Color.Gray
                        )
                        val text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = Roboto,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("43")
                            }
                            append("/60")
                        }

                        Text(text = text)
                    }
                    Text(
                        text = "Tugas Saya",
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            fontFamily = Roboto,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                        ),
                        color = Green
                    )
                }
                PercentageBar(float = 43 / 60f)
            }
        }
    }
}

@Composable
fun BerandaAtas(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.725f)
                    .background(Green)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.275f)
//                                .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            NamaDanFoto()
            Spacer(modifier = Modifier.height(20.dp))
            PengaduanCard()
        }
    }
}

@Composable
fun PercentageBar(float: Float) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp),
        progress = float,
        color = DarkGreen,
        backgroundColor = Color.LightGray
    )

}

//@Preview
//@Composable
//fun PersentageBarPreview() {
//    LaporinSarprasTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            PercentageBar(0.5f)
//        }
//    }
//}

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
            BerandaScreen(navController, viewModel(factory = BerandaViewModel.Factory))
        }
    }
}