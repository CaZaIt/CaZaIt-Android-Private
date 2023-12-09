package org.cazait.core.data.datasource.request

data class RefreshTokenRequest(
    val userId: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
)
