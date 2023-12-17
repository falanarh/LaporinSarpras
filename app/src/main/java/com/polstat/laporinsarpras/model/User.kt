package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val roles: List<String>,
    val position: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val address: String
)