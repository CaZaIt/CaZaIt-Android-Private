package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class FindUserIdRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: FindUserIdDTO
)

data class FindUserIdDTO(
    @SerializedName("accountName")
    val userId: String
)

data class CheckUserDataRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: CheckUserDataDTO
)

data class CheckUserDataDTO(
    @SerializedName("accountName")
    val userId: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)