package org.cazait.core.model.sign

data class SignInInfo(
    val userId: String,
    val uuid: String,
    val accessToken: String,
    val refreshToken: String,
    val role: String,
)
