package com.polstat.laporinsarpras.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.polstat.laporinsarpras.LaporinSarprasApplication
import com.polstat.laporinsarpras.model.Pengaduan
import com.polstat.laporinsarpras.model.User
import com.polstat.laporinsarpras.repository.PengaduanRepository
import com.polstat.laporinsarpras.repository.UserPreferencesRepository
import com.polstat.laporinsarpras.repository.UserRepository
import com.polstat.laporinsarpras.repository.UserState
import com.polstat.laporinsarpras.response.ListPengaduanResponse
import com.polstat.laporinsarpras.response.UserResponse
import kotlinx.coroutines.launch

private const val TAG = "PengaduanMendesakViewModel"

class PengaduanMendesakViewModel (
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val pengaduanRepository: PengaduanRepository
) : ViewModel(){
    private lateinit var listPengaduanResponse : ListPengaduanResponse
    private lateinit var listPengaduanMendesak: List<Pengaduan>
    private lateinit var userState: UserState
    private lateinit var userResponse: UserResponse
    var tokenUser = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWxhbmFAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9LT09SRElOQVRPUiJdLCJpc3MiOiJQb2xzdGF0IiwiaWF0IjoxNzAyODA1NjY2LCJleHAiOjE3MDI4OTIwNjZ9.z2_gv8T89xETnRU0XkC39xke4VhVN9soN1xhLsn6yqEgpavljNKp64UtbpiYX5mbX9WzDqQfe96ZgR3b1ELpjw"
    var pengaduanMendesakUiState: PengaduanMendesakUiState by mutableStateOf(PengaduanMendesakUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.user.collect {
                userState = it
                try {
                    println("Mau eksekusi 1")
                    userResponse = userRepository.showProfile(it.token)
                    println("Mau eksekusi 2")
                    getPengaduanMendesaks()
                } catch (e: Exception) {
                    Log.e(TAG, e.stackTraceToString())
                }
            }
        }
    }

    fun isKoordinator(): Boolean {
        return userState.isKoordinator
    }

    fun isTeknisi(): Boolean {
        return userState.isTeknisi
    }

    fun isPelapor(): Boolean {
        return userState.isPelapor
    }

    suspend fun getPengaduanMendesaks() {
        println("Mau masuk")
        println(userState.token)
        try {
            if (userState.isKoordinator){
                listPengaduanResponse = pengaduanRepository.getAllPengaduans(userState.token)
                println(listPengaduanResponse.message)
            } else if (userState.isTeknisi){
                listPengaduanResponse = pengaduanRepository.getSomePengaduans(userState.token)
                println(listPengaduanResponse.message)
            }
            listPengaduanMendesak = filterPengaduanBerdasarkanPrioritas(listPengaduanResponse.data, "TINGGI")
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
            return
        }

        Log.d(TAG, "pengaduanMendesak: ${listPengaduanMendesak}")
        pengaduanMendesakUiState = PengaduanMendesakUiState.Success(listPengaduanMendesak)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LaporinSarprasApplication)
                PengaduanMendesakViewModel(
                    userPreferencesRepository = application.userPreferenceRepository,
                    userRepository = application.container.userRepository,
                    pengaduanRepository = application.container.pengaduanRepository
                )
            }
        }
    }
}

fun filterPengaduanBerdasarkanPrioritas(pengaduans: List<Pengaduan>?, prioritas: String): List<Pengaduan> {
    return try {
        pengaduans?.filter { it.prioritas == prioritas } ?: emptyList()
    } catch (e: NullPointerException) {
        emptyList()
    }
}


enum class PengaduanMendesakOperationResult {
    Success,
    Error
}

sealed interface PengaduanMendesakUiState {
    data class Success(val pengaduanMendesaks: List<Pengaduan>): PengaduanMendesakUiState
    object Error: PengaduanMendesakUiState
    object Loading: PengaduanMendesakUiState
}