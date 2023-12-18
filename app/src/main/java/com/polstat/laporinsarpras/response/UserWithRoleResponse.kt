package com.polstat.laporinsarpras.response

import com.polstat.laporinsarpras.model.UserWithRole

data class UserWithRoleResponse (
    val status: Int?,
    val message: String?,
    val data: UserWithRole?
)