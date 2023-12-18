package com.polstat.laporinsarpras.repository

import com.polstat.laporinsarpras.model.Aset
import com.polstat.laporinsarpras.response.AsetResponse
import com.polstat.laporinsarpras.response.ListAsetResponse
import com.polstat.laporinsarpras.ui.screen.Screen
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AsetRepository {
    suspend fun addAset(token: String, aset: Aset)
    suspend fun getAsets(token: String) : ListAsetResponse
    suspend fun getAsetById(token: String, barangId: Long) : AsetResponse
    suspend fun editAset(token: String, aset: Aset)
    suspend fun deleteAset(token: String, barangId: Long)
}

//interface AsetApiService {
//    @POST("/barangs")
//    suspend fun addAset(@Header("Authorization") token: String, @Body aset: Aset)
//    @GET("/barangs")
//    suspend fun getAsets(@Header("Authorization") token: String) : ListAsetResponse
//    @GET("/barangs/search")
//    suspend fun getAsetById(@Header("Authorization") token: String, @Query("barangId") barangId: Long) : AsetResponse
//    @PUT("/barangs")
//    suspend fun editAset(@Header("Authorization") token: String, @Body aset: Aset)
//    @DELETE("/barangs")
//    suspend fun deleteAset(@Header("Authorization") token: String, @Query("barangId") barangId: Long)
//}