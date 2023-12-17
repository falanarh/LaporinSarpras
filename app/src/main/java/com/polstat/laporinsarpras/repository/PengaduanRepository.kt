package com.polstat.laporinsarpras.repository

import com.polstat.laporinsarpras.model.AddPengaduanForm
import com.polstat.laporinsarpras.model.ConfirmPengaduan
import com.polstat.laporinsarpras.network.PengaduanApiService
import com.polstat.laporinsarpras.response.ListPengaduanResponse
import com.polstat.laporinsarpras.response.PengaduanResponse

interface PengaduanRepository {
    suspend fun addPengaduan(token: String, addPengaduanForm: AddPengaduanForm)
    suspend fun confirmPengaduan(token: String, confirmPengaduan: ConfirmPengaduan)
    suspend fun getAllPengaduans(token: String) : ListPengaduanResponse
    suspend fun getSomePengaduans(token: String) : ListPengaduanResponse
    suspend fun getPengaduanById(token: String, pengaduanId: Long) : PengaduanResponse
}

class NetworkPengaduanRepository(private val pengaduanApiService: PengaduanApiService): PengaduanRepository {
    override suspend fun addPengaduan(token: String, addPengaduanForm: AddPengaduanForm) = pengaduanApiService.addPengaduan("Bearer $token", addPengaduanForm)
    override suspend fun confirmPengaduan(token: String, confirmPengaduan: ConfirmPengaduan) = pengaduanApiService.confirmPengaduan("Bearer $token", confirmPengaduan)
    override suspend fun getAllPengaduans(token: String) = pengaduanApiService.getPengaduansAll("Bearer $token")
    override suspend fun getSomePengaduans(token: String) = pengaduanApiService.getPengaduansSome("Bearer $token")
    override suspend fun getPengaduanById(token: String, pengaduanId: Long) = pengaduanApiService.getPengaduanById("Bearer $token", pengaduanId)
}

