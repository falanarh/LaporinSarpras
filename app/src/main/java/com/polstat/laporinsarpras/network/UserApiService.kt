package com.polstat.laporinsarpras.network

import com.polstat.laporinsarpras.model.DeleteAccountForm
import com.polstat.laporinsarpras.model.EditPasswordForm
import com.polstat.laporinsarpras.model.EditProfileForm
import com.polstat.laporinsarpras.response.ListUserWithRoleResponse
import com.polstat.laporinsarpras.response.UserResponse
import com.polstat.laporinsarpras.response.UserWithRoleResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface UserApiService {
    @GET("/users")
    suspend fun getAllUser(@Header("Authorization") token: String) : ListUserWithRoleResponse
    @GET("/users/profile")
    suspend fun showProfile(@Header("Authorization") token: String): UserResponse
    @PATCH("/users/update_profile")
    suspend fun updateProfile(@Header("Authorization") token: String, @Body editProfileForm: EditProfileForm) : UserResponse
    @PATCH("/users/set_new_password")
    suspend fun setNewPassword(@Header("Authorization") token: String, @Body editPasswordForm: EditPasswordForm)
    @DELETE("/users/delete")
    suspend fun deleteAccountUser(@Header("Authorization") token: String, @Body deleteAccountForm: DeleteAccountForm)
}