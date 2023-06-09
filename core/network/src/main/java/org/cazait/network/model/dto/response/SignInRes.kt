package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class SignInRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val signInInfo: SignInInfoDTO,
)

data class SignInInfoDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("email")
    val email: String,
    @SerializedName("jwtToken")
    val jwtToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("role")
    val role: String,
)
