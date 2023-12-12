package com.polstat.laporinsarpras.ui.screen

import LoginViewModel
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polstat.laporinsarpras.R
import com.polstat.laporinsarpras.ui.state.EmailState
import com.polstat.laporinsarpras.ui.state.PasswordState
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.theme.LightGray
import com.polstat.laporinsarpras.ui.theme.Red
import com.polstat.laporinsarpras.ui.theme.Typography.Roboto
import com.polstat.laporinsarpras.ui.theme.Typography.body1
import com.polstat.laporinsarpras.ui.theme.Typography.h1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Komponen UI
@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {
    val emailState = remember { EmailState() }
    val passwordState = remember { PasswordState() }
    val loginResponse by loginViewModel.loginResponse.observeAsState()
    val showToast = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Green),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth()
                .background(
                    color = LightGray,
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                        topEnd = 50.dp
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .height(8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {}
            Column {
                Spacer(modifier = Modifier.height(15.dp))
                LoginPicture()
                Spacer(modifier = Modifier.height(15.dp))
                LoginTitle()
                Spacer(modifier = Modifier.height(15.dp))
                EmailTextField(emailState)
                Spacer(modifier = Modifier.height(5.dp))
                PasswordTextField(passwordState)
                Spacer(modifier = Modifier.height(15.dp))
                LoginButton(emailState, passwordState, loginViewModel) {
                    loginResponse?.let {
                        if (it.data != null){
                            navController.navigate("success")
                        } else {
                            showToast.value = true
//                       Toast.makeText(LocalContext.current, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                if (showToast.value) {
                    Toast.makeText(LocalContext.current, "${loginResponse?.message}" + ": Email dan/atau password salah!", Toast.LENGTH_SHORT).show()
                    showToast.value = false // Reset the state
                }



            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(emailState: EmailState) {
    val focusRequester = remember { FocusRequester() }
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
    OutlinedTextField(
        value = emailState.email,
        onValueChange = {
            emailState.email = it
            emailState.validateEmail()
        },
        label = {
            Text(
                text = "Email",
                fontFamily = Roboto,
                fontSize = 14.sp
            )
        },
        isError = !emailState.isEmailValid,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .focusRequester(focusRequester)
            .onFocusChanged { emailState.isEmailFocused = it.isFocused },
        maxLines = 1
    )
    if (!emailState.isEmailValid && !emailState.isEmailFocused) {
        Text(
            text = emailState.emailErrorMessage,
            color = Red,
            fontFamily = Roboto,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(passwordState: PasswordState) {
    val focusRequester = remember { FocusRequester() }
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
    OutlinedTextField(
        value = passwordState.password,
        onValueChange = {
            passwordState.password = it
            passwordState.validatePassword()
        },
        label = {
            Text(
                text = "Password",
                fontFamily = Roboto,
                fontSize = 14.sp
            )
        },
        isError = !passwordState.isPasswordValid,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .focusRequester(focusRequester)
            .onFocusChanged { passwordState.isPasswordFocused = it.isFocused },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        maxLines = 1
    )
    if (!passwordState.isPasswordValid && !passwordState.isPasswordFocused) {
        Text(
            text = passwordState.passwordErrorMessage,
            color = Red,
            fontFamily = Roboto,
            fontSize = 14.sp
        )
    }
}

@Composable
fun LoginTitle() {
    Column {
        Text(
            text = "LaporinSarpras.",
            color = Color.Black,
            style = h1
        )
        Text(
            text = "Masuk dan buat aduan mengenai permasalahan\nsarana dan prasarana kelas",
            color = Color.Gray,
            style = body1
        )
    }
}

@Composable
fun LoginButton(emailState: EmailState, passwordState: PasswordState, loginViewModel: LoginViewModel, onLoginClick: () -> Unit) {
    val isButtonEnabled = emailState.email.isNotEmpty() && passwordState.password.isNotEmpty()
    val coroutineScope = rememberCoroutineScope()
    ElevatedButton(
        onClick = {
            coroutineScope.launch {
                loginViewModel.login(emailState.email, passwordState.password)
                // Panggil lambda onLoginClick saat tombol diklik
                delay(500)
                onLoginClick.invoke()
            }
        },
        enabled = isButtonEnabled,
        modifier = Modifier
            .fillMaxWidth(0.85f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green,
            contentColor = Color.Black
        ),
    ) {
        Text(
            text = "Masuk",
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun LoginPicture(){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.login_picture),
            contentDescription = "Login Picture",
            modifier = Modifier
                .fillMaxWidth(0.8f)
        )
    }
}

//@Preview
//@Composable
//fun EmailPreview() {
//    LaporinSarprasTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            val emailState = remember { EmailState() }
//            Column {
//                EmailTextField(emailState)
//            }
//        }
//    }
//}

//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LaporinSarprasTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            LoginScreen()
//        }
//    }
//}