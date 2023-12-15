package com.polstat.laporinsarpras.repository

import com.polstat.laporinsarpras.model.LoginRequest
import com.polstat.laporinsarpras.model.LoginResponse
import com.polstat.laporinsarpras.network.RetrofitInstance

class LoginRepository {
    private val loginApiService = RetrofitInstance.loginApiService

    suspend fun requestForLogin(loginRequest: LoginRequest): LoginResponse {
        return loginApiService.login(loginRequest)
    }
}
