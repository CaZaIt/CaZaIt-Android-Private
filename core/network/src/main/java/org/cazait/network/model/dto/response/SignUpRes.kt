package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class SignUpRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val signUpInfo: SignUpInfoDTO,
)

data class SignUpInfoDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val idNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("nickname")
    val nickname: String,
)
