package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerifyCodeResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: PhoneNumber,
)

@JsonClass(generateAdapter = true)
data class PhoneNumber(
    @Json(name = "recipientPhoneNumber")
    val recipientPhoneNumber: String,
)