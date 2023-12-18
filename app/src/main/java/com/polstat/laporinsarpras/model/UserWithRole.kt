package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable

@Serializable
data class UserWithRole (
    val userId: Long,
    val roles: List<Role>,
    val position: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val address: String,
)