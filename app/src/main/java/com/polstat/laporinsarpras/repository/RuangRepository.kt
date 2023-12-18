package com.polstat.laporinsarpras.repository

import com.polstat.laporinsarpras.model.Ruang
import com.polstat.laporinsarpras.network.RuangApiService
import com.polstat.laporinsarpras.response.ListRuangResponse
import com.polstat.laporinsarpras.response.RuangResponse

interface RuangRepository {
    suspend fun addRuang(token: String, ruang: Ruang)
    suspend fun getRuangs(token: String) : ListRuangResponse
    suspend fun getRuangById(token: String, ruangId: String) : RuangResponse
    suspend fun editRuang(token: String, ruang: Ruang)
    suspend fun deleteRuang(token: String, ruangId: String)
}

class NetworkRuangRepository(private val ruangApiService: RuangApiService) : RuangRepository {
    override suspend fun addRuang(token: String, ruang: Ruang) = ruangApiService.addRuang("Bearer ${token}", ruang)
    override suspend fun getRuangs(token: String): ListRuangResponse = ruangApiService.getRuangs("Bearer ${token}")
    override suspend fun getRuangById(token: String, ruangId: String): RuangResponse = ruangApiService.getRuangById("Bearer ${token}", ruangId)
    override suspend fun editRuang(token: String, ruang: Ruang) = ruangApiService.editRuang("Bearer ${token}", ruang)
    override suspend fun deleteRuang(token: String, ruangId: String) = ruangApiService.deleteRuang("Bearer ${token}", ruangId)
}