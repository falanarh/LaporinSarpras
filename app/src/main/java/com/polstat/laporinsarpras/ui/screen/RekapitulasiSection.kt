package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChairAlt
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.SmsFailed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polstat.laporinsarpras.ui.theme.Gray
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.theme.LightGray
import com.polstat.laporinsarpras.ui.theme.Roboto

@Composable
fun RekapitulasiSection(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),

        ) {
            Text(
                text = "Rekapitulasi",
                style = TextStyle(
                    fontFamily = Roboto,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Pengaduan()
        Spacer(modifier = Modifier.height(20.dp))
        Aset()
        Spacer(modifier = Modifier.height(20.dp))
        Ruang()
    }
}

@Composable
fun Pengaduan(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val jmlPengaduan = "25"

                Text(
                    text = "Pengaduan",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.Gray
                )
                Text(
                    text = jmlPengaduan,
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Jumlah Saat Ini",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Filled.SmsFailed,
                    contentDescription = "Pengaduan",
                    tint = Green,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Lihat Detail",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Green,
                    modifier = Modifier.clickable {  }
                )
            }
        }
    }
}

@Composable
fun Aset(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val jmlAset = "68"

                Text(
                    text = "Aset",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.Gray
                )
                Text(
                    text = jmlAset,
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Jumlah Saat Ini",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Filled.ChairAlt,
                    contentDescription = "Ruang",
                    tint = Green,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Lihat Detail",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Green,
                    modifier = Modifier.clickable {  }
                )
            }
        }
    }
}

@Composable
fun Ruang(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val jmlRuang = "36"

                Text(
                    text = "Ruang",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.Gray
                )
                Text(
                    text = jmlRuang,
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Jumlah Saat Ini",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Filled.HomeWork,
                    contentDescription = "Ruang",
                    tint = Green,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Lihat Detail",
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Green,
                    modifier = Modifier.clickable {  }
                )
            }
        }
    }
}

@Preview
@Composable
fun RekapitulasiSectionPreview() {
    Surface(
        color = LightGray,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RekapitulasiSection()
    }
}