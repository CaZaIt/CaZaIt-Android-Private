package org.cazait.network.model.dto.request

data class RefreshTokenReq(
    val id: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
)
