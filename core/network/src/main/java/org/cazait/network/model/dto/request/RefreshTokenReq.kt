package org.cazait.network.model.dto.request

data class RefreshTokenReq(
    val userIdx: Long,
    val rol: String,
    val accessToken: String,
    val refreshToken: String,
)
