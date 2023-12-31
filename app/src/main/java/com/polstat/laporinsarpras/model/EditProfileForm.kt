package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable

@Serializable
data class EditProfileForm(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val address: String
)
