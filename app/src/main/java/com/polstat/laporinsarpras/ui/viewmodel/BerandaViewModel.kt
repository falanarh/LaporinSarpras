package com.polstat.laporinsarpras.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.polstat.laporinsarpras.LaporinSarprasApplication
import com.polstat.laporinsarpras.repository.UserPreferencesRepository
import com.polstat.laporinsarpras.repository.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BerandaViewModel (
    private  val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private  val _uiState = MutableStateFlow(LaporinSarprasUiState())
    val uiState: StateFlow<LaporinSarprasUiState> = _uiState.asStateFlow()

    val userState: StateFlow<UserState> = userPreferencesRepository.user.map { user ->
        user
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserState(
            token = "",
            name = "",
            email = "",
            isTeknisi = false,
            isKoordinator = false,
            isPelapor = false
        )
    )

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
                    userPreferencesRepository = application.userPreferenceRepository
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