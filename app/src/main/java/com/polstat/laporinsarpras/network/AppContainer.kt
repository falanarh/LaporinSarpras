package com.polstat.laporinsarpras.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.polstat.laporinsarpras.repository.AsetRepository
import com.polstat.laporinsarpras.repository.NetworkAsetRepository
import com.polstat.laporinsarpras.repository.NetworkPengaduanRepository
import com.polstat.laporinsarpras.repository.NetworkRuangRepository
import com.polstat.laporinsarpras.repository.NetworkUserRepository
import com.polstat.laporinsarpras.repository.PengaduanRepository
import com.polstat.laporinsarpras.repository.RuangRepository
import com.polstat.laporinsarpras.repository.UserRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val userRepository: UserRepository
    val pengaduanRepository: PengaduanRepository
    val asetRepository: AsetRepository
    val ruangRepository: RuangRepository
}

class DefaultAppContainer() : AppContainer {
    private val baseUrl = "http://192.168.1.13:2912/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(
            OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build())
        .build()

    //API Service
    private val userApiService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    private val pengaduanApiService: PengaduanApiService by lazy {
        retrofit.create(PengaduanApiService::class.java)
    }

    private val asetApiService: AsetApiService by lazy {
        retrofit.create(AsetApiService::class.java)
    }

    private val ruangApiService: RuangApiService by lazy {
        retrofit.create(RuangApiService::class.java)
    }

    //Repository
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userApiService)
    }

    override val pengaduanRepository: PengaduanRepository by lazy {
        NetworkPengaduanRepository(pengaduanApiService)
    }

    override val asetRepository: AsetRepository by lazy {
        NetworkAsetRepository(asetApiService)
    }

    override val ruangRepository: RuangRepository by lazy {
        NetworkRuangRepository(ruangApiService)
    }
}