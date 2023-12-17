package com.polstat.laporinsarpras.network

import com.polstat.laporinsarpras.model.AddPengaduanForm
import com.polstat.laporinsarpras.model.ConfirmPengaduan
import com.polstat.laporinsarpras.model.Pengaduan
import com.polstat.laporinsarpras.response.ListPengaduanResponse
import com.polstat.laporinsarpras.response.PengaduanResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PengaduanApiService {
    @POST("/pengaduans")
    suspend fun addPengaduan(@Header("Authorization") token: String, @Body addPengaduanForm: AddPengaduanForm)
    @POST("/pengaduans/confirm")
    suspend fun confirmPengaduan(@Header("Authorization") token: String, @Body confirmPengaduan: ConfirmPengaduan)
    @GET("pengaduans/all")
    suspend fun getPengaduansAll(@Header("Authorization") token: String) : ListPengaduanResponse
    @GET("pengaduans/some")
    suspend fun getPengaduansSome(@Header("Authorization") token: String,) : ListPengaduanResponse
    @GET("/pengaduans/search")
    suspend fun getPengaduanById(@Header("Authorization") token: String, @Query("pengaduanId") pengaduanId: Long): PengaduanResponse
}