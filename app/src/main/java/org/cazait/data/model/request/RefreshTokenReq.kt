package org.cazait.data.model.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenReq(
    val userIdx: Long,
    val rol: String,
    @SerializedName("Authorization") val accessToken: String,
    @SerializedName("REFRESH-TOKEN") val refreshToken: String,
)
