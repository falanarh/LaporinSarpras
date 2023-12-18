package com.polstat.laporinsarpras.repository

import com.polstat.laporinsarpras.model.DeleteAccountForm
import com.polstat.laporinsarpras.model.EditPasswordForm
import com.polstat.laporinsarpras.model.EditProfileForm
import com.polstat.laporinsarpras.network.UserApiService
import com.polstat.laporinsarpras.response.ListUserWithRoleResponse
import com.polstat.laporinsarpras.response.UserResponse

interface UserRepository {
    suspend fun getAllUser(token: String) : ListUserWithRoleResponse
    suspend fun showProfile(token: String): UserResponse
    suspend fun updateProfile(token: String, editProfileForm: EditProfileForm) : UserResponse
    suspend fun setNewPassword(token: String, editPasswordForm: EditPasswordForm)
    suspend fun deleteAccountUser(token: String, deleteAccountForm: DeleteAccountForm)
}

class NetworkUserRepository(private val userApiService: UserApiService) : UserRepository {
    override suspend fun getAllUser(token: String): ListUserWithRoleResponse = userApiService.getAllUser("Bearer $token")
    override suspend fun showProfile(token: String) : UserResponse = userApiService.showProfile("Bearer $token")
    override suspend fun updateProfile(token: String, editProfileForm: EditProfileForm) : UserResponse = userApiService.updateProfile("Bearer $token", editProfileForm)
    override suspend fun setNewPassword(token: String, editPasswordForm: EditPasswordForm) = userApiService.setNewPassword("Bearer $token", editPasswordForm)
    override suspend fun deleteAccountUser(token: String, deleteAccountForm: DeleteAccountForm) = userApiService.deleteAccountUser("Bearer $token", deleteAccountForm)
}