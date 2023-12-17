package com.polstat.laporinsarpras.model

data class ConfirmPengaduan (
    val pengaduanId: Long,
    val keputusan: String,
    val keterangan: String,
    val emailTeknisi: String
)
