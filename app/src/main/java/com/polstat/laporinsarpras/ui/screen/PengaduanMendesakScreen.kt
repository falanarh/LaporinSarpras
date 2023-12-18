package com.polstat.laporinsarpras.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.model.Pengaduan
import com.polstat.laporinsarpras.model.UserWithRole
import com.polstat.laporinsarpras.ui.theme.Blue
import com.polstat.laporinsarpras.ui.theme.DarkGreen
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.theme.LaporinSarprasTheme
import com.polstat.laporinsarpras.ui.theme.Red
import com.polstat.laporinsarpras.ui.theme.Roboto
import com.polstat.laporinsarpras.ui.utils.Constants
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakOperationResult
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakUiState
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PengaduanMendesakScreen(navController: NavController, pengaduanMendesakViewModel: PengaduanMendesakViewModel) {
    Scaffold (
        topBar = {
                 AppToolbar(toolbarTitle = "Pengaduan Mendesak", refreshIconClicked = {navController.navigate("pengaduanMendesak")}) {
                     navController.navigate("beranda")
                 }
        },
        bottomBar = {},
    ) {innerPadding->
        when (pengaduanMendesakViewModel.pengaduanMendesakUiState) {
            is PengaduanMendesakUiState.Error -> {
//            Text(text = stringResource(id = R.string.error))
                println("Error")
            }
            is PengaduanMendesakUiState.Loading -> {
//            Text(text = "Loading")
                println("Loading")
            }
            is PengaduanMendesakUiState.Success -> {
                println("Sukses")
                val pengaduanMendesaks = (pengaduanMendesakViewModel.pengaduanMendesakUiState as PengaduanMendesakUiState.Success).pengaduanMendesaks
                val cards = pengaduanMendesakViewModel.cards.collectAsState()
                val expandedCard = pengaduanMendesakViewModel.expandedCardList.collectAsState()
                
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    item { 
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    items(pengaduanMendesaks) {pengaduan ->
                        ExpandablePengaduanMendesak(
                            card = pengaduan,
                            onCardArrowClick = { pengaduanMendesakViewModel.cardArrowClick(pengaduan.pengaduanId) },
                            expanded = expandedCard.value.contains(pengaduan.pengaduanId),
                            navController = navController,
                            viewModel = pengaduanMendesakViewModel
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandablePengaduanMendesak(
    card: Pengaduan,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    navController: NavController,
    viewModel: PengaduanMendesakViewModel
) {
    val transitionState = remember { MutableTransitionState(expanded).apply {
        targetState = !expanded
    }}
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = Constants.ExpandAnimation)
    }, label = "bgColorTransition") {
        if (expanded) Green else Green
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = Constants.ExpandAnimation)
    }, label = "paddingTransition") {
        20.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = Constants.ExpandAnimation)
    }, label = "elevationTransition") {
        if (expanded) 20.dp else 5.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = Constants.ExpandAnimation,
            easing = FastOutSlowInEasing
        )
    }, label = "cornersTransition") {
        10.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = Constants.ExpandAnimation)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }

    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(0.85f)
                    ) {
                        Text(
                            text = "Ruang: ${card.ruangId} [${card.date}]",
                            color = Color.White,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(0.15f)
                    ) {
                        CardArrow(
                            degrees = arrowRotationDegree,
                            onClick = onCardArrowClick
                        )
                    }
                }
            }
            ExpandablePengaduanMendesak(expanded, card, navController, viewModel)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ExpandablePengaduanMendesak(expanded: Boolean = true, card: Pengaduan, navController: NavController, viewModel: PengaduanMendesakViewModel) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = Constants.FadeInAnimation,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(Constants.ExpandAnimation))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = Constants.FadeOutAnimation,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(Constants.CollapseAnimation))
    }
    val openDialog = remember { mutableStateOf(false) }
    val openTerimaDialog = remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = expanded,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("ID: ")
                    }
                    append("${card.pengaduanId}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Email Pelapor: ")
                    }
                    append("${card.emailPelapor}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Email Teknisi: ")
                    }
                    append(if (card.emailTeknisi.isNullOrEmpty()) "-" else card.emailTeknisi)
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Barang ID: ")
                    }
                    append("${card.barangId}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Ruang ID: ")
                    }
                    append("${card.ruangId}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Tanggal: ")
                    }
                    append("${card.date}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Prioritas: ")
                    }
                    append("${card.prioritas}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Status: ")
                    }
                    append("${card.status}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Keterangan: ")
                    }
                    append(card.keterangan ?: "-")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Deskripsi: ")
                    }
                    append("${card.deskripsi}")
                },
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (card.status == "MENUNGGU") {
                            openTerimaDialog.value = true
                        }
                    },
                    enabled = card.status == "MENUNGGU",
                    shape = RoundedCornerShape(8.dp), // Atur sudut bulat di sini
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue
                    ),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = "Terima",
                        style = TextStyle(
                            fontFamily = Roboto,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                }
                Button(
                    onClick = {
                        if (card.status == "MENUNGGU") {
                            openDialog.value = true
                        }
                    },
                    enabled = card.status == "MENUNGGU",
                    shape = RoundedCornerShape(8.dp), // Atur sudut bulat di sini
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red
                    ),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = "Tolak",
                        style = TextStyle(
                            fontFamily = Roboto,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                }
            }
            ConfirmationDialog(openDialog = openDialog, onDismiss = { openDialog.value = false }, navController, viewModel, card)
            TerimaDialog(openTerimaDialog = openTerimaDialog, onDismiss = { openTerimaDialog.value = false }, navController, viewModel, card)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationDialog(openDialog: MutableState<Boolean>, onDismiss: () -> Unit, navController: NavController, viewModel: PengaduanMendesakViewModel, pengaduan: Pengaduan) {
    var keterangan by remember { mutableStateOf("") }
    val openToast = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Konfirmasi Penolakan")
            },
            text = {
                Column {
                    Text("Apakah Anda yakin ingin menolak?")
                    OutlinedTextField(
                        value = keterangan,
                        onValueChange = {
                            keterangan = it
                            viewModel.updateKeterangan(it)
                        },
                        label = { Text("Keterangan") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        scope.launch {
                            viewModel.tolakPengaduan(pengaduan)
                        }
                        openToast.value = true
                    },
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
                    ),
                    shape = RoundedCornerShape(5.dp), // Atur sudut bulat di sini
                ) {
                    Text(
                        text = "Yakin",
                        fontFamily = Roboto,
                        fontSize = 16.sp,
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        // Handle cancellation here
                    },
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(5.dp), // Atur sudut bulat di sini
                ) {
                    Text(
                        text = "Batal",
                        fontFamily = Roboto,
                        fontSize = 16.sp
                    )
                }
            }
        )
    }
    if (openToast.value){
        if (viewModel.setTolakStatusResult.value == PengaduanMendesakOperationResult.Success){
            Toast.makeText(LocalContext.current, "Berhasil menolak pengaduan!", Toast.LENGTH_SHORT).show()
            navController.navigate("pengaduanMendesak")
        } else {
            Toast.makeText(LocalContext.current, "Gagal menolak pengaduan!", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerimaDialog(openTerimaDialog: MutableState<Boolean>, onDismiss: () -> Unit, navController: NavController, viewModel: PengaduanMendesakViewModel, pengaduan: Pengaduan) {
    val openToast = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (openTerimaDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openTerimaDialog.value = false
            },
            title = {
                Text(text = "Penugasan Pengaduan")
            },
            text = {
                Column {
                    Text("Pilih Teknisi :")
                    DropdownMenuTeknisi(viewModel)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openTerimaDialog.value = false
                        scope.launch {
                            viewModel.terimaPengaduan(pengaduan)
                        }
                        openToast.value = true
                    },
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
                    ),
                    shape = RoundedCornerShape(5.dp), // Atur sudut bulat di sini
                ) {
                    Text(
                        text = "Yakin",
                        fontFamily = Roboto,
                        fontSize = 16.sp,
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openTerimaDialog.value = false
                        // Handle cancellation here
                    },
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(5.dp), // Atur sudut bulat di sini
                ) {
                    Text(
                        text = "Batal",
                        fontFamily = Roboto,
                        fontSize = 16.sp
                    )
                }
            }
        )
    }
    if (openToast.value){
        if (viewModel.setTolakStatusResult.value == PengaduanMendesakOperationResult.Success){
            Toast.makeText(LocalContext.current, "Berhasil menerima pengaduan!", Toast.LENGTH_SHORT).show()
            navController.navigate("pengaduanMendesak")
        } else {
            Toast.makeText(LocalContext.current, "Gagal menerima pengaduan!", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun DropdownMenuTeknisi(viewModel: PengaduanMendesakViewModel) {
    var daftarTeknisi: List<UserWithRole> by remember { mutableStateOf(emptyList()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    var scope = rememberCoroutineScope()

    daftarTeknisi = viewModel.listUserWithRole.value

    if(viewModel.getAllTeknisiResult.value == PengaduanMendesakOperationResult.Success) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopStart)
        ) {
            Text(
                if (selectedIndex >= 0) daftarTeknisi[selectedIndex].firstName else "Pilih satu teknisi",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { expanded = true })
                    .background(Color.LightGray)
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.66f)
            ) {
                daftarTeknisi.forEachIndexed { index, teknisi ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                        scope.launch {
                            viewModel.updateSelectedTeknisi(teknisi)
                        }
                    }) {
                        Text(text = teknisi.firstName)
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DropdownMenuTeknisiPreview() {
//    DropdownMenuTeknisi(viewModel = viewModel(factory = PengaduanMendesakViewModel.Factory))
//}


//@Preview(showBackground = true)
//@Composable
//fun DropdownMenuDemoPreview() {
//    DropdownMenuTeknisi()
//}



@Preview
@Composable
fun PengaduanMendesakPreview() {
    LaporinSarprasTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
//            val pengaduanMendesakViewModel = PengaduanMendesakViewModel()
//            PengadauanMendesakScreen(navController, pengaduanMendesakViewModel)
        }
    }
}