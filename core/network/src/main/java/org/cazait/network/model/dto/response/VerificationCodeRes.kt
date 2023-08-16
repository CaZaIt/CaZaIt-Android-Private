package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class VerificationCodeRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: CodeDTO
)

data class CodeDTO(
    @SerializedName("recipientPhoneNumber")
    val recipientPhoneNumber: String,
    @SerializedName("requestTime")
    val requestTime: String
)
