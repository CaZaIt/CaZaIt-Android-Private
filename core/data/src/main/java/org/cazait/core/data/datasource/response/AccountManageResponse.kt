package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckPasswordResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: org.cazait.core.data.datasource.response.CheckPasswordDTO,
)

@JsonClass(generateAdapter = true)
data class CheckPasswordDTO(
    @Json(name = "id")
    val userUuid: String,
    @Json(name = "accountName")
    val userId: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "nickname")
    val nickname: String,
)

@JsonClass(generateAdapter = true)
data class ChangePasswordResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: org.cazait.core.data.datasource.response.ChangePasswordDTO,
)

@JsonClass(generateAdapter = true)
data class ChangePasswordDTO(
    @Json(name = "id")
    val userUuid: String,
    @Json(name = "accountName")
    val userId: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "nickname")
    val nickname: String,
)

@JsonClass(generateAdapter = true)
data class ChangeNicknameResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: org.cazait.core.data.datasource.response.ChangeNicknameDTO,
)

@JsonClass(generateAdapter = true)
data class ChangeNicknameDTO(
    @Json(name = "id")
    val userUuid: String,
    @Json(name = "accountName")
    val userId: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "nickname")
    val nickname: String,
)
