package com.polstat.laporinsarpras.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.polstat.laporinsarpras.model.Pengaduan
import com.polstat.laporinsarpras.model.User
import com.polstat.laporinsarpras.ui.theme.DarkGreen
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.theme.LaporinSarprasTheme
import com.polstat.laporinsarpras.ui.theme.Roboto
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakOperationResult
import com.polstat.laporinsarpras.ui.viewmodel.PengaduanMendesakViewModel
import com.polstat.laporinsarpras.ui.viewmodel.ProfilViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilScreen(navController: NavController, viewModel: ProfilViewModel){
    val currentScreen = mutableStateOf<Screen>(Screen.Profil)
    val userProfile : User?
    val openEditDialog = remember { mutableStateOf(false) }
    val openEditPwDialog = remember { mutableStateOf(false) }

    if (viewModel.getProfileResult.value == ProfilViewModel.ProfileOperationResult.Success){
        userProfile = viewModel.userProfile.value
        Scaffold(
            topBar = {
                AppToolbar(toolbarTitle = "Profil", refreshIconClicked = { navController.navigate("profil")}) {
                    navController.navigate("beranda")
                }
            },
            bottomBar = {
                CustomBottomNavigation(
                    navController = navController,
                    currentScreenId = Screen.Profil.id
                ){
                    currentScreen.value = it
                    navController.navigate(it.id)
                }
            }
        ) { innerPadding->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Green,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(80.dp)
                                .align(Alignment.Center)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                top = 50.dp, // Add margin to the top
                                end = 10.dp,
                                bottom = 10.dp
                            )
                            .shadow(8.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Color.Gray,
                        ),
                        border = BorderStroke(1.dp, Color.White),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Nama Depan",
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                userProfile?.let {
                                    Text(
                                        text = it.firstName,
                                        fontFamily = Roboto,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                                println("Haloo 3")
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Nama Belakang",
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                userProfile?.let {
                                    Text(
                                        text = it.lastName,
                                        fontFamily = Roboto,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Email",
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                userProfile?.let {
                                    Text(
                                        text = it.email,
                                        fontFamily = Roboto,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Nomor Telepon",
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                userProfile?.let {
                                    Text(
                                        text = it.phoneNumber,
                                        fontFamily = Roboto,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Role",
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                userProfile?.let {
                                    Text(
                                        text = it.roles[0],
                                        fontFamily = Roboto,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    ElevatedButton(
                        onClick = {
                            openEditDialog.value = true
                        },
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp
                            )
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp
                        )
                    ) {
                        Text(
                            text = "Edit Profil",
                            fontFamily = Roboto,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    //Spacer(modifier = Modifier.height(5.dp))

                    ElevatedButton(
                        onClick = {
                            openEditPwDialog.value = true
                        },
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp
                            )
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp
                        )
                    ) {
                        Text(
                            text = "Ubah Password",
                            fontFamily = Roboto,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    //Spacer(modifier = Modifier.height(5.dp))

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp
                            )
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Green
                        ),
                    ){
                        Text(
                            text = "Logout",
                            fontFamily = Roboto,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    EditProfilDialog(openDialog = openEditDialog, onDismiss = { openEditDialog.value = false }, navController, viewModel)
                    UbahPasswordDialog(openDialog = openEditPwDialog, onDismiss = { openEditPwDialog.value = false }, navController, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfilDialog(openDialog: MutableState<Boolean>, onDismiss: () -> Unit, navController: NavController, viewModel: ProfilViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    val openToast = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    firstName = viewModel.userProfile.value?.firstName ?: ""
    lastName = viewModel.userProfile.value?.lastName ?: ""
    phoneNumber = viewModel.userProfile.value?.phoneNumber ?: ""

//    viewModel.updateFirstName(firstName)
//    viewModel.updateLastName(lastName)
//    viewModel.updatePhoneNumber(phoneNumber)

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "Ubah Profil",
                    fontFamily = Roboto
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = {
                            firstName = it
//                            scope.launch {
                            viewModel.updateFirstName(it)
//                            }
                        },
                        label = { Text("First Name") }
                    )
                    LaunchedEffect(firstName) {
                        viewModel.updateFirstName(firstName)
                    }
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = {
                            lastName = it
//                            scope.launch {
                                viewModel.updateLastName(it)
//                            }
                        },
                        label = { Text("Last Name") }
                    )
                    LaunchedEffect(lastName) {
                        viewModel.updateLastName(lastName)
                    }
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
//                            scope.launch {
                                viewModel.updatePhoneNumber(it)
//                            }
                        },
                        label = { Text("Nomor Telepon") }
                    )
                    LaunchedEffect(phoneNumber) {
                        viewModel.updatePhoneNumber(phoneNumber)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        scope.launch {
                            viewModel.editProfile()
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
                        text = "Ubah",
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
        if (viewModel.editProfileResult.value == ProfilViewModel.ProfileOperationResult.Success){
            Toast.makeText(LocalContext.current, "Berhasil mengedit profil!", Toast.LENGTH_SHORT).show()
            navController.navigate("profil")
        } else {
            Toast.makeText(LocalContext.current, "Gagal mengedit profil!", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UbahPasswordDialog(openDialog: MutableState<Boolean>, onDismiss: () -> Unit, navController: NavController, viewModel: ProfilViewModel) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordVisibility2 by remember { mutableStateOf(false) }
    val openToast = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "Ubah Password",
                    fontFamily = Roboto
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        label = { Text("Password Lama") },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, contentDescription = "visibility")
                            }
                        }
                    )
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Password Baru") },
                        visualTransformation = if (passwordVisibility2) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility2 = !passwordVisibility2 }) {
                                Icon(if (passwordVisibility2) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, contentDescription = "visibility")
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        scope.launch {
                            viewModel.editPw()
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
                        text = "Ubah",
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
        if (viewModel._editPwResult.value == ProfilViewModel.ProfileOperationResult.Success){
            Toast.makeText(LocalContext.current, "Berhasil mengedit password!", Toast.LENGTH_SHORT).show()
            navController.navigate("profil")
        } else {
            Toast.makeText(LocalContext.current, "Gagal mengedit password!", Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview
@Composable
fun ProfilScreenPreview() {
    LaporinSarprasTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
//            ProfilScreen(navController)
        }
    }
}