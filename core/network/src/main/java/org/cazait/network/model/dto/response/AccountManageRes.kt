package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class CheckPasswordRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: CheckPasswordDTO
)

data class CheckPasswordDTO(
    @SerializedName("id")
    val userUuid: String,
    @SerializedName("accountName")
    val userId: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("nickname")
    val nickname: String
)

data class ChangePasswordRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ChangePasswordDTO
)

data class ChangePasswordDTO(
    @SerializedName("id")
    val userUuid: String,
    @SerializedName("accountName")
    val userId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("nickname")
    val nickname: String
)

data class ChangeNicknameRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ChangeNicknameDTO
)

data class ChangeNicknameDTO(
    @SerializedName("id")
    val userUuid: String,
    @SerializedName("accountName")
    val userId: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("nickname")
    val nickname: String
)
