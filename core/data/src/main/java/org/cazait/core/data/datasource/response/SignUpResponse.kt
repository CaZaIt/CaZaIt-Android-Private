package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val signUpInfo: SignUpInfoDTO,
)

@JsonClass(generateAdapter = true)
data class SignUpInfoDTO(
    @Json(name = "id")
    val uuid: String,
    @Json(name = "accountName")
    val userId: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "nickname")
    val nickname: String,
)
