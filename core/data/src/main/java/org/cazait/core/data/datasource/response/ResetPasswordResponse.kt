package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResetPasswordResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: ResetPasswordDTO,
)

@JsonClass(generateAdapter = true)
data class ResetPasswordDTO(
    @Json(name = "id")
    val uuid: String,
    @Json(name = "accountName")
    val userId: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "nickname")
    val nickname: String,
)
