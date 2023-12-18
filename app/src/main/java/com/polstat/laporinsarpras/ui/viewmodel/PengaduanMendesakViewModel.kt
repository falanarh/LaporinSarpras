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
import com.polstat.laporinsarpras.model.ConfirmPengaduan
import com.polstat.laporinsarpras.model.Pengaduan
import com.polstat.laporinsarpras.model.UserWithRole
import com.polstat.laporinsarpras.repository.PengaduanRepository
import com.polstat.laporinsarpras.repository.UserPreferencesRepository
import com.polstat.laporinsarpras.repository.UserRepository
import com.polstat.laporinsarpras.repository.UserState
import com.polstat.laporinsarpras.response.ListPengaduanResponse
import com.polstat.laporinsarpras.response.ListUserWithRoleResponse
import com.polstat.laporinsarpras.response.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "PengaduanMendesakViewModel"

class PengaduanMendesakViewModel (
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val pengaduanRepository: PengaduanRepository
) : ViewModel(){
    private lateinit var listPengaduanResponse : ListPengaduanResponse
    private lateinit var listPengaduanMendesak: List<Pengaduan>
    private lateinit var listUserWithRoleResponse: ListUserWithRoleResponse
    private lateinit var userState: UserState
    private lateinit var userResponse: UserResponse
    val _cards = MutableStateFlow(listOf<Pengaduan>())
    val cards: StateFlow<List<Pengaduan>> get() = _cards
    val _expandedCardList = MutableStateFlow(listOf<Long>())
    val expandedCardList: StateFlow<List<Long>> get() = _expandedCardList
    val _setTolakStatusResult = MutableStateFlow<PengaduanMendesakOperationResult>(PengaduanMendesakOperationResult.Success)
    val setTolakStatusResult: StateFlow<PengaduanMendesakOperationResult> get() = _setTolakStatusResult
    val _setTerimaStatusResult = MutableStateFlow<PengaduanMendesakOperationResult>(PengaduanMendesakOperationResult.Success)
    val setTerimaStatusResult: StateFlow<PengaduanMendesakOperationResult> get() = _setTerimaStatusResult
    val _getAllTeknisiResult = MutableStateFlow<PengaduanMendesakOperationResult>(PengaduanMendesakOperationResult.Success)
    val getAllTeknisiResult: StateFlow<PengaduanMendesakOperationResult> get() = _getAllTeknisiResult
    var keterangan by mutableStateOf("")
        private set
    val initialTeknisi = UserWithRole(
        userId = 0L,
        roles = emptyList(),
        position = "",
        email = "",
        password = "",
        firstName = "",
        lastName = "",
        phoneNumber = "",
        address = ""
    )

    var selectedTeknisi by mutableStateOf(initialTeknisi)

    var pengaduanMendesakUiState: PengaduanMendesakUiState by mutableStateOf(PengaduanMendesakUiState.Loading)
        private set
    val _listUserWithRole = MutableStateFlow(listOf<UserWithRole>())
    val listUserWithRole: StateFlow<List<UserWithRole>> get() = _listUserWithRole

    init {
        viewModelScope.launch {
            userPreferencesRepository.user.collect {
                userState = it
                try {
                    println("Mau eksekusi 1")
                    userResponse = userRepository.showProfile(it.token)
                    println("Mau eksekusi 2")
                    getPengaduanMendesaks()
                    getAllTeknisi()
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

    fun updateKeterangan(newKeterangan: String) {
        keterangan = newKeterangan
    }

    fun updateSelectedTeknisi(newTeknisi: UserWithRole){
        selectedTeknisi = newTeknisi
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
        _cards.emit(listPengaduanMendesak)
    }

    suspend fun getAllTeknisi(){
        try {
            if (userState.isKoordinator) {
                listUserWithRoleResponse = userRepository.getAllUser(userState.token)
                _listUserWithRole.value = filterTeknisi(listUserWithRoleResponse.data)
                _getAllTeknisiResult.value = PengaduanMendesakOperationResult.Success
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
            return
        }
    }

    fun filterTeknisi(daftarUser: List<UserWithRole>): List<UserWithRole> {
        return daftarUser.filter { user ->
            user.roles.any { role ->
                role.name == "ROLE_TEKNISI"
            }
        }
    }


    suspend fun tolakPengaduan(pengaduan: Pengaduan) {
        val tolakPengaduan = ConfirmPengaduan(
            keputusan = "DITOLAK",
            pengaduanId = pengaduan.pengaduanId,
            emailTeknisi = "",
            keterangan = keterangan
        )
        Log.d(TAG, tolakPengaduan.toString())
        viewModelScope.launch {
            try {
                pengaduan.pengaduanId?.let { pengaduanRepository.confirmPengaduan(userState.token, tolakPengaduan) }
                _setTolakStatusResult.value = PengaduanMendesakOperationResult.Success
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
                _setTolakStatusResult.value = PengaduanMendesakOperationResult.Error
            }
        }
    }

    suspend fun terimaPengaduan(pengaduan: Pengaduan){
        val terimaPengaduan = ConfirmPengaduan(
            keputusan = "DIKERJAKAN",
            pengaduanId = pengaduan.pengaduanId,
            emailTeknisi = selectedTeknisi.email,
            keterangan = keterangan
        )
        Log.d(TAG, terimaPengaduan.toString())
        viewModelScope.launch {
            try {
                pengaduan.pengaduanId?.let { pengaduanRepository.confirmPengaduan(userState.token, terimaPengaduan) }
                _setTerimaStatusResult.value = PengaduanMendesakOperationResult.Success
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
                _setTerimaStatusResult.value = PengaduanMendesakOperationResult.Error
            }
        }
    }

//    suspend fun deleteReport(report: Report): ReportOperationResult {
//        try {
//            report.id?.let { reportRepository.deleteReport(userState.token, it) }
//        } catch (e: Exception) {
//            Log.e(TAG, "Error: ${e.message}")
//            return ReportOperationResult.Error
//        }
//
//        return ReportOperationResult.Success
//    }

    fun cardArrowClick(cardId: Long) {
        _expandedCardList.value = _expandedCardList.value.toMutableList().also { list ->
            if (list.contains(cardId)) {
                list.remove(cardId)
            } else {
                list.add(cardId)
            }
        }
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