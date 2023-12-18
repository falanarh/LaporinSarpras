package com.polstat.laporinsarpras.network

import com.polstat.laporinsarpras.model.Ruang
import com.polstat.laporinsarpras.response.ListRuangResponse
import com.polstat.laporinsarpras.response.RuangResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface RuangApiService {
    @POST("/ruangs")
    suspend fun addRuang(@Header("Authorization") token: String, @Body ruang: Ruang)
    @GET("/ruangs")
    suspend fun getRuangs(@Header("Authorization") token: String) : ListRuangResponse
    @GET("/ruangs/search")
    suspend fun getRuangById(@Header("Authorization") token: String, @Query("ruangId") ruangId: String) : RuangResponse
    @PUT("/ruangs")
    suspend fun editRuang(@Header("Authorization") token: String, ruang: Ruang)
    @DELETE("/ruangs")
    suspend fun deleteRuang(@Header("Authorization") token: String, ruangId: String)
}