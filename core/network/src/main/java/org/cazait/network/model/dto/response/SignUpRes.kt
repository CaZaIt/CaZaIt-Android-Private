package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

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
    val uuid: String,
    @SerializedName("accountNumber")
    val userId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("nickname")
    val nickname: String,
)
