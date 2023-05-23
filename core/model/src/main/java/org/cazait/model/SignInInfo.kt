package org.cazait.model

data class SignInInfo(
    val email: String,
    val id: Long,
    val jwtToken: String,
    val refreshToken: String,
    val role: String,
)
