package com.polstat.laporinsarpras.repository

import com.polstat.laporinsarpras.model.DeleteAccountForm
import com.polstat.laporinsarpras.model.EditPasswordForm
import com.polstat.laporinsarpras.model.EditProfileForm
import com.polstat.laporinsarpras.model.User
import com.polstat.laporinsarpras.network.UserApiService
import com.polstat.laporinsarpras.response.UserResponse

interface UserRepository {
    suspend fun showProfile(token: String): UserResponse
    suspend fun updateProfile(token: String, editProfileForm: EditProfileForm)
    suspend fun setNewPassword(token: String, editPasswordForm: EditPasswordForm)
    suspend fun deleteAccountUser(token: String, deleteAccountForm: DeleteAccountForm)
}

class NetworkUserRepository(private val userApiService: UserApiService) : UserRepository {
    override suspend fun showProfile(token: String) = userApiService.showProfile("Bearer $token")
    override suspend fun updateProfile(token: String, editProfileForm: EditProfileForm) = userApiService.updateProfile("Bearer $token", editProfileForm)
    override suspend fun setNewPassword(token: String, editPasswordForm: EditPasswordForm) = userApiService.setNewPassword("Bearer $token", editPasswordForm)
    override suspend fun deleteAccountUser(token: String, deleteAccountForm: DeleteAccountForm) = userApiService.deleteAccountUser("Bearer $token", deleteAccountForm)
}