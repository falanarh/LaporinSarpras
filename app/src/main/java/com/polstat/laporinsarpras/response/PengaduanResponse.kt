package com.polstat.laporinsarpras.response

import com.polstat.laporinsarpras.model.Pengaduan
import kotlinx.serialization.Serializable

@Serializable
data class PengaduanResponse (
    val status: Int?,
    val message: String?,
    val data: Pengaduan?
)