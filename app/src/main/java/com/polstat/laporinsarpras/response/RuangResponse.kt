package com.polstat.laporinsarpras.response

import com.polstat.laporinsarpras.model.Ruang
import kotlinx.serialization.Serializable

@Serializable
data class RuangResponse (
    val status: Int?,
    val message: String?,
    val data: Ruang?,
)