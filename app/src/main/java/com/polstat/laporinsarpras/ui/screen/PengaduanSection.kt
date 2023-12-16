package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.polstat.laporinsarpras.R
import com.polstat.laporinsarpras.ui.theme.Gray
import com.polstat.laporinsarpras.ui.theme.LightGray
import com.polstat.laporinsarpras.ui.theme.LightRed
import com.polstat.laporinsarpras.ui.theme.Red
import com.polstat.laporinsarpras.ui.theme.Roboto

val pengaduanCard = listOf(
    "Mendesak",
    "Terbaru"
)

@Composable
fun PengaduanSection(){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Pengaduan",
            style = TextStyle(
                fontFamily = Roboto,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(start = 32.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow{
            items(pengaduanCard.size){index ->
                PengaduanCardItem(index = index)
            }
        }
    }
}

@Composable
fun PengaduanCardItem(index: Int){
    var firstItemPaddingStart = 16.dp
    var lastItemPaddingEnd = 0.dp

    if (index == 0) {
        firstItemPaddingStart = 32.dp
    }

    if (index == pengaduanCard.size - 1) {
        lastItemPaddingEnd = 20.dp
    }

    var image = painterResource(id = R.drawable.pengaduan_mendesak)
    if (pengaduanCard[index] == "Terbaru") {
        image = painterResource(id = R.drawable.pengaduan_terbaru)
    }

    Box(
        modifier = Modifier
            .padding(
                start = firstItemPaddingStart,
                end = lastItemPaddingEnd
            )
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = Gray)
                .width(250.dp)
                .height(160.dp)
                .padding(vertical = 12.dp, horizontal = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = pengaduanCard[index],
                    style = TextStyle(
                        fontFamily = Roboto,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    GradientButton(
                        text = "Buka",
                        textColor = Color.Black,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(Red, LightRed)
                        )
                    ) {}
                    Image(
                        painter = image,
                        modifier = Modifier.fillMaxHeight(),
                        contentDescription = pengaduanCard[index]
                    )
                }
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = { onClick() })
    {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Preview
@Composable
fun PengaduanSectionPreview() {
    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        PengaduanSection()
    }
}
