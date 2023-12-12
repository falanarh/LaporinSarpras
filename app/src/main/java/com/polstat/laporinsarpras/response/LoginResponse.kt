package com.polstat.laporinsarpras.response

data class LoginResponse(
    val status: Int?,
    val message: String?,
    val data: LoginData?,
)

class LoginData {
    val email: String = ""
    val accessToken: String = ""
}