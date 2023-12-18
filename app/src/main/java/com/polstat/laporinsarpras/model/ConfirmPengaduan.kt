package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmPengaduan (
    val pengaduanId: Long,
    val keputusan: String,
    val keterangan: String,
    val emailTeknisi: String
)
