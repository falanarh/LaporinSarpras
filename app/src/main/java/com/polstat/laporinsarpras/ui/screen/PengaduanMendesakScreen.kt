package com.polstat.laporinsarpras.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.ui.theme.LaporinSarprasTheme
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakOperationResult
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakUiState
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengaduanMendesakScreen(navController: NavController, pengaduanMendesakViewModel: PengaduanMendesakViewModel) {
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
            println(pengaduanMendesaks.size)
//            LazyColumn(modifier = Modifier.fillMaxHeight()) {
//                items(items = pengaduanMendesaks) { report ->
//                    ReportItemCard(
//                        description = report.description,
//                        problemType = report.problemType.name,
//                        reporter = report.reporter!!.name,
//                        reportedDate = toLocalFormat(report.reportedDate),
//                        status = if (report.solved) stringResource(id = R.string.selesai) else stringResource(id = R.string.belum_selesai),
//                        options = {
//                            Column {
//                                if (isAdmin) {
//                                    DrawerNavigationItem(
//                                        icons = Icons.Filled.Delete,
//                                        text = R.string.hapus_laporan,
//                                        onClick = { onDeleteClicked(report) }
//                                    )
//                                } else if (isSupervisor) {
//                                    DrawerNavigationItem(
//                                        icons = Icons.Filled.Info,
//                                        text = R.string.ubah_status,
//                                        onClick = { onChangeStatusClicked(report) }
//                                    )
//                                } else {
//                                    DrawerNavigationItem(
//                                        icons = Icons.Filled.Edit,
//                                        text = R.string.edit_laporan,
//                                        onClick = { onEditClicked(report) }
//                                    )
//                                }
//                            }
//                        }
//                    )
//                }
//            }
        }
    }


    Scaffold (
        topBar = {
                 AppToolbar(toolbarTitle = "Pengaduan Mendesak") {
                     navController.navigate("beranda")
                 }
        },
        bottomBar = {},
    ) {innerPadding->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {

            }
        }
    }
}

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