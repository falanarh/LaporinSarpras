package com.polstat.laporinsarpras.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.polstat.laporinsarpras.LaporinSarprasApplication
import com.polstat.laporinsarpras.model.LoginRequest
import com.polstat.laporinsarpras.model.LoginResponse
import com.polstat.laporinsarpras.repository.LoginRepository
import com.polstat.laporinsarpras.repository.UserPreferencesRepository
import com.polstat.laporinsarpras.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

private const val TAG = "LoginViewModel"

class LoginViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val loginRepository = LoginRepository()

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun requestLogin(email: String, password: String) {
        println("Masuk sini")
        viewModelScope.launch {
            try {
                println("Masuk sini 2")
                val response = loginRepository.requestForLogin(LoginRequest(email, password))
                _loginResponse.value = response
                _loginResult.value = LoginResult.Success
                Log.d(TAG, "accessToken: ${loginResponse.value?.data?.accessToken}")
                println(response)

                loginResponse.value?.data?.accessToken?.let { userPreferencesRepository.saveToken(it) }

                val userResponse = loginResponse.value?.data?.accessToken?.let {
                    userRepository.showProfile(
                        it
                    )
                }
                val isKoordinator = userResponse?.data?.roles?.any { role -> role == "ROLE_KOORDINATOR" }
                val isTeknisi = userResponse?.data?.roles?.any { role -> role == "ROLE_TEKNISI" }
                val isPelapor = userResponse?.data?.roles?.any { role -> role == "ROLE_PELAPOR" }

                Log.d(TAG, "name: ${userResponse?.data?.firstName} ${userResponse?.data?.lastName}")
                Log.d(TAG, "email: ${userResponse?.data?.email}")
                Log.d(TAG, "isKoordinator: $isKoordinator")
                Log.d(TAG, "isTeknisi: $isTeknisi")
                Log.d(TAG, "isPelapor: $isPelapor")

                userPreferencesRepository.saveName("${userResponse?.data?.firstName} ${userResponse?.data?.lastName}")
                userPreferencesRepository.saveEmail("${userResponse?.data?.email}")
                userPreferencesRepository.saveIsKoordinator(isKoordinator ?: false)
                userPreferencesRepository.saveIsTeknisi(isTeknisi ?: false)
                userPreferencesRepository.saveIsPelapor(isPelapor ?: false)
            } catch(e: HttpException) {
                when (e.code()) {
                    400 -> {
                        Log.d(TAG, "bad input")
                        _loginResult.value = LoginResult.BadInput
                    }
                    401 -> {
                        Log.d(TAG, "Wrong email or password")
                        _loginResult.value = LoginResult.WrongEmailOrPassword
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Can't login: (${e.javaClass}) ${e.message}")
                Log.e(TAG, e.stackTraceToString())
                _loginResult.value = LoginResult.NetworkError
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LaporinSarprasApplication)
                val userRepository = application.container.userRepository
                LoginViewModel(
                    userPreferencesRepository = application.userPreferenceRepository,
                    userRepository = userRepository
                )
            }
        }
    }
}

enum class LoginResult {
    Success,
    BadInput,
    WrongEmailOrPassword,
    NetworkError
}
