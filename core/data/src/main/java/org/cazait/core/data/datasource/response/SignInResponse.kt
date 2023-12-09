package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val signInInfo: SignInInfoDTO,
)

@JsonClass(generateAdapter = true)
data class SignInInfoDTO(
    @Json(name = "id")
    val uuid: String,
    @Json(name = "accountName")
    val userId: String,
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String,
    @Json(name = "role")
    val role: String,
)
