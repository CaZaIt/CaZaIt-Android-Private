package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class RefreshTokenRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: RefreshTokenDTO,
)

data class RefreshTokenDTO(
    @SerializedName("id")
    val uuid: String,
    @SerializedName("accountNumber")
    val userId: String,
    @SerializedName("jwtToken")
    val jwtToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("role")
    val role: String,
)
