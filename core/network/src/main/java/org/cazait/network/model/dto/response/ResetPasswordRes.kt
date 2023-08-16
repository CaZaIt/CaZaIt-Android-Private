package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class ResetPasswordRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ResetPasswordDTO
)

data class ResetPasswordDTO(
    @SerializedName("id")
    val uuid: String,
    @SerializedName("accountName")
    val userId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("nickname")
    val nickname: String,
)
