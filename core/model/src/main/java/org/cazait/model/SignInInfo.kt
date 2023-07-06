package org.cazait.model

data class SignInInfo(
    val email: String,
    val id: String,
    val accessToken: String,
    val refreshToken: String,
    val role: String,
)
