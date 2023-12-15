package com.polstat.laporinsarpras.network

import com.polstat.laporinsarpras.model.LoginRequest
import com.polstat.laporinsarpras.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}