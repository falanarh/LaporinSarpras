package com.polstat.laporinsarpras.ui.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.polstat.laporinsarpras.LaporinSarprasApplication
import com.polstat.laporinsarpras.model.EditPasswordForm
import com.polstat.laporinsarpras.model.EditProfileForm
import com.polstat.laporinsarpras.model.User
import com.polstat.laporinsarpras.repository.UserPreferencesRepository
import com.polstat.laporinsarpras.repository.UserRepository
import com.polstat.laporinsarpras.repository.UserState
import com.polstat.laporinsarpras.response.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "ProfilViewModel"
class ProfilViewModel (
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private  val _uiState = MutableStateFlow(LaporinSarprasUiState())
    val uiState: StateFlow<LaporinSarprasUiState> = _uiState.asStateFlow()

    private lateinit var userState: UserState

    private lateinit var userResponse: UserResponse

    val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> get() = _userProfile
    val _getProfileResult = MutableStateFlow<ProfileOperationResult>(ProfileOperationResult.Success)
    val getProfileResult: StateFlow<ProfileOperationResult> get() = _getProfileResult
    val _editProfileResult = MutableStateFlow<ProfileOperationResult>(ProfileOperationResult.Success)
    val editProfileResult: StateFlow<ProfileOperationResult> get() = _editProfileResult
    val _editPwResult = MutableStateFlow<ProfileOperationResult>(ProfileOperationResult.Success)
    val editPwResult: StateFlow<ProfileOperationResult> get() = _editPwResult

    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set

    var oldPassword by mutableStateOf("")
        private set
    var newPassword by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.user.collect{
                userState = it
                try {
                    getProfile()
                } catch (e: Exception) {
                    Log.e(TAG, e.stackTraceToString())
                }
            }
        }
    }

    fun updateFirstName(newFirstName: String) {
        firstName = newFirstName
    }

    fun updateLastName(newLastName: String) {
        lastName = newLastName
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
    }

    fun updateOldPassword(newOldPassword: String){
        oldPassword = newOldPassword
    }

    fun updateNewPassword(newNewPassword: String){
        newPassword = newNewPassword
    }

    suspend fun getProfile(){
        try {
            userResponse = userRepository.showProfile(userState.token)
            _userProfile.value = userResponse.data
            _getProfileResult.value = ProfileOperationResult.Success
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
            _getProfileResult.value = ProfileOperationResult.Error
            return
        }
    }

    suspend fun editProfile(){
        val address: String = userProfile.value?.address ?: ""
        val editProfileForm = EditProfileForm(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            address = address
        )
        try {
            userResponse = userRepository.updateProfile(userState.token, editProfileForm)
            _userProfile.value = userResponse.data
            _editProfileResult.value = ProfileOperationResult.Success
        } catch (e: Exception){
            Log.e(TAG, "Error: ${e.message}")
            _editProfileResult.value = ProfileOperationResult.Error
            return
        }
    }

    suspend fun editPw(){
        val editPwd = EditPasswordForm(
            oldPassword = oldPassword,
            newPassword = newPassword
        )
        try {
            userRepository.setNewPassword(userState.token, editPwd)
            _editPwResult.value = ProfileOperationResult.Success
        } catch (e: Exception){
            Log.e(TAG, "Error: ${e.message}")
            _editPwResult.value = ProfileOperationResult.Error
            return
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

    enum class ProfileOperationResult {
        Success,
        Error
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LaporinSarprasApplication)
                ProfilViewModel(
                    userPreferencesRepository = application.userPreferenceRepository,
                    userRepository = application.container.userRepository
                )
            }
        }
    }
}
