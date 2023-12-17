package com.polstat.laporinsarpras.response

import com.polstat.laporinsarpras.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse (
    val status: Int?,
    val message: String?,
    val data: User?
)