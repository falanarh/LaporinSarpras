package com.polstat.laporinsarpras.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.236.221.194:8085/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val loginApiService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}