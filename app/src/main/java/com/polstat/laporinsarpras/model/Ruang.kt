package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable

@Serializable
data class Ruang (
    val ruangId: String?,
    val gedung: Int?,
    val lantai: Int?,
    val keterangan: String?
)