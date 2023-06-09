package org.cazait.network.model.dto.request

data class RefreshTokenReq(
    val userIdx: Long,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
)
