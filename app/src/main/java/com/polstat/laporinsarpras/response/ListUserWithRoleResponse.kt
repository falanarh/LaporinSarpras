package com.polstat.laporinsarpras.response

import com.polstat.laporinsarpras.model.UserWithRole
import kotlinx.serialization.Serializable

@Serializable
data class ListUserWithRoleResponse (
    val status: Int?,
    val message: String?,
    val data: List<UserWithRole>
)