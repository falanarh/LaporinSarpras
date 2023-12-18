package com.polstat.laporinsarpras.repository

import com.polstat.laporinsarpras.model.Aset
import com.polstat.laporinsarpras.network.AsetApiService
import com.polstat.laporinsarpras.response.AsetResponse
import com.polstat.laporinsarpras.response.ListAsetResponse

interface AsetRepository {
    suspend fun addAset(token: String, aset: Aset)
    suspend fun getAsets(token: String) : ListAsetResponse
    suspend fun getAsetById(token: String, barangId: String) : AsetResponse
    suspend fun editAset(token: String, aset: Aset)
    suspend fun deleteAset(token: String, barangId: String)
}

class NetworkAsetRepository(private val asetApiService: AsetApiService) : AsetRepository {
    override suspend fun addAset(token: String, aset: Aset) =
        asetApiService.addAset("Bearer ${token}", aset)

    override suspend fun getAsets(token: String): ListAsetResponse =
        asetApiService.getAsets("Bearer ${token}")

    override suspend fun getAsetById(token: String, barangId: String): AsetResponse =
        asetApiService.getAsetById("Bearer ${token}", barangId)

    override suspend fun editAset(token: String, aset: Aset) =
        asetApiService.editAset("Bearer ${token}", aset)

    override suspend fun deleteAset(token: String, barangId: String) =
        asetApiService.deleteAset("Bearer ${token}", barangId)
}