package com.polstat.laporinsarpras.response

import com.polstat.laporinsarpras.model.Aset
import kotlinx.serialization.Serializable

@Serializable
data class AsetResponse (
    val status: Int?,
    val message: String?,
    val data: Aset?
)