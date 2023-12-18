package com.polstat.laporinsarpras.network

import com.polstat.laporinsarpras.model.Aset
import com.polstat.laporinsarpras.response.AsetResponse
import com.polstat.laporinsarpras.response.ListAsetResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AsetApiService {
    @POST("/barangs")
    suspend fun addAset(@Header("Authorization") token: String, @Body aset: Aset)
    @GET("/barangs")
    suspend fun getAsets(@Header("Authorization") token: String) : ListAsetResponse
    @GET("/barangs/search")
    suspend fun getAsetById(@Header("Authorization") token: String, @Query("barangId") barangId: Long) : AsetResponse
    @PUT("/barangs")
    suspend fun editAset(@Header("Authorization") token: String, @Body aset: Aset)
    @DELETE("/barangs")
    suspend fun deleteAset(@Header("Authorization") token: String, @Query("barangId") barangId: Long)
}