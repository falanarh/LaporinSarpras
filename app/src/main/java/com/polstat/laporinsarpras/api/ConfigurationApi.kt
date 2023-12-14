package com.polstat.laporinsarpras.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigurationApi {
    // Koneksi ke API menggunakan Retrofit
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.80:2912/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}