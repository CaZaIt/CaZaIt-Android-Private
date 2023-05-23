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
    val id: Long,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("nickname")
    val nickname: String,
)
