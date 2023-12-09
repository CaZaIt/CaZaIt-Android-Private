package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerificationCodeResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: CodeDTO,
)

@JsonClass(generateAdapter = true)
data class CodeDTO(
    @Json(name = "recipientPhoneNumber")
    val recipientPhoneNumber: String,
    @Json(name = "requestTime")
    val requestTime: String,
)
