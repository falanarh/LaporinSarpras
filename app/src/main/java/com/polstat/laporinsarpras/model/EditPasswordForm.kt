package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable

@Serializable
data class EditPasswordForm(
    val oldPassword: String,
    val newPassword: String
)
