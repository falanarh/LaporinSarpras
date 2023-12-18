package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable

@Serializable
data class Role (
    val roleId: Int,
    val name: String
)