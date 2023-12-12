package com.polstat.laporinsarpras.api

import com.polstat.laporinsarpras.request.LoginRequest
import com.polstat.laporinsarpras.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}