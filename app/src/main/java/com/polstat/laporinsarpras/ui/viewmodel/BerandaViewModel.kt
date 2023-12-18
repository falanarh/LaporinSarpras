package com.polstat.laporinsarpras.ui.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.polstat.laporinsarpras.LaporinSarprasApplication
import com.polstat.laporinsarpras.model.Aset
import com.polstat.laporinsarpras.model.Pengaduan
import com.polstat.laporinsarpras.model.Ruang
import com.polstat.laporinsarpras.repository.AsetRepository
import com.polstat.laporinsarpras.repository.PengaduanRepository
import com.polstat.laporinsarpras.repository.RuangRepository
import com.polstat.laporinsarpras.repository.UserPreferencesRepository
import com.polstat.laporinsarpras.repository.UserState
import com.polstat.laporinsarpras.response.ListAsetResponse
import com.polstat.laporinsarpras.response.ListPengaduanResponse
import com.polstat.laporinsarpras.response.ListRuangResponse
import com.polstat.laporinsarpras.ui.screen.Pengaduan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "BerandaViewModel"

class BerandaViewModel (
    private val userPreferencesRepository: UserPreferencesRepository,
    private val pengaduanRepository: PengaduanRepository,
    private val asetRepository: AsetRepository,
    private val ruangRepository: RuangRepository
) : ViewModel() {
    private  val _uiState = MutableStateFlow(LaporinSarprasUiState())
    val uiState: StateFlow<LaporinSarprasUiState> = _uiState.asStateFlow()

//    val userState: StateFlow<UserState> = userPreferencesRepository.user.map { user ->
//        user
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = UserState(
//            token = "",
//            name = "",
//            email = "",
//            isTeknisi = false,
//            isKoordinator = false,
//            isPelapor = false
//        )
//    )
    private lateinit var userState: UserState
    private lateinit var listPengaduanResponse: ListPengaduanResponse
    private lateinit var listAsetResponse: ListAsetResponse
    private lateinit var listRuangResponse: ListRuangResponse
    val _listPengaduan = MutableStateFlow(listOf<Pengaduan>())
    val listPengaduan: StateFlow<List<Pengaduan>> get() = _listPengaduan
    val _listPengaduanProses = MutableStateFlow(listOf<Pengaduan>())
    val listPengaduanProses: StateFlow<List<Pengaduan>> get() = _listPengaduanProses
    val _listAset = MutableStateFlow(listOf<Aset>())
    val listAset: StateFlow<List<Aset>> get() = _listAset
    val _listRuang = MutableStateFlow(listOf<Ruang>())
    val listRuang: StateFlow<List<Ruang>> get() = _listRuang

    init {
        viewModelScope.launch {
            userPreferencesRepository.user.collect{
                userState = it
                try {
                    getAllPengaduan()
                    getAllAset()
                    getAllRuang()
                } catch (e: Exception) {
                    Log.e(TAG, e.stackTraceToString())
                }
            }
        }
    }

//    init {
//        viewModelScope.launch {
//            userPreferencesRepository.user.collect {
//                userState = it
//                try {
//                    println("Mau eksekusi 1")
//                    userResponse = userRepository.showProfile(it.token)
//                    println("Mau eksekusi 2")
//                    getPengaduanMendesaks()
//                } catch (e: Exception) {
//                    Log.e(TAG, e.stackTraceToString())
//                }
//            }
//        }
//    }

    suspend fun getAllPengaduan() {
        try {
            if (userState.isKoordinator){
                listPengaduanResponse = pengaduanRepository.getAllPengaduans(userState.token)
                println(listPengaduanResponse.message)
            } else if (userState.isTeknisi){
                listPengaduanResponse = pengaduanRepository.getSomePengaduans(userState.token)
                println(listPengaduanResponse.message)
            }
            _listPengaduan.value = listPengaduanResponse.data
            _listPengaduanProses.value = filterPengaduanKecualiStatus(
                pengaduans = filterPengaduanKecualiStatus(listPengaduanResponse.data, "MENUNGGU"),
                status = "DITOLAK"
            )

        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
            return
        }
    }

    suspend fun getAllAset(){
        try {
            listAsetResponse = asetRepository.getAsets(userState.token)
            _listAset.value = listAsetResponse.data
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
            return
        }
    }

    suspend fun getAllRuang(){
        try {
            listRuangResponse = ruangRepository.getRuangs(userState.token)
            _listRuang.value = listRuangResponse.data!!
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
            return
        }
    }

    fun filterPengaduanKecualiStatus(pengaduans: List<Pengaduan>, status: String): List<Pengaduan> {
        return try {
            pengaduans?.filter { it.status != status } ?: emptyList()
        } catch (e: NullPointerException) {
            emptyList()
        }
    }

    fun showSpinner() {
        _uiState.update { currentState ->
            currentState.copy(
                showProgressDialog = true
            )
        }
    }

    fun dismissSpinner() {
        _uiState.update { currentState ->
            currentState.copy(
                showProgressDialog = false
            )
        }
    }

    fun showMessageDialog(@StringRes title: Int, @StringRes body: Int) {
        _uiState.update {  currentState ->
            currentState.copy(
                showProgressDialog = false,
                showMessageDialog = true,
                messageTitle = title,
                messageBody = body
            )
        }
    }

    fun dismissMessageDialog() {
        _uiState.update {  currentState ->
            currentState.copy(
                showMessageDialog = false
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.saveToken("")
            userPreferencesRepository.saveName("")
            userPreferencesRepository.saveEmail("")
            userPreferencesRepository.saveIsTeknisi(false)
            userPreferencesRepository.saveIsKoordinator(false)
            userPreferencesRepository.saveIsPelapor(false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LaporinSarprasApplication)
                BerandaViewModel(
                    userPreferencesRepository = application.userPreferenceRepository,
                    pengaduanRepository = application.container.pengaduanRepository,
                    asetRepository = application.container.asetRepository,
                    ruangRepository = application.container.ruangRepository
                )
            }
        }
    }
}


data class LaporinSarprasUiState(
    val showProgressDialog: Boolean = false,
    val showMessageDialog: Boolean = false,
    @StringRes val messageTitle: Int = 0,
    @StringRes val messageBody: Int = 0
)