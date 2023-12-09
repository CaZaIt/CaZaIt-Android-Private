package org.cazait.core.data.datasource.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FindUserIdResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: FindUserIdDTO,
)

@JsonClass(generateAdapter = true)
data class FindUserIdDTO(
    @Json(name = "accountName")
    val userId: String,
)

@JsonClass(generateAdapter = true)
data class CheckUserDataResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: CheckUserDataDTO,
)

@JsonClass(generateAdapter = true)
data class CheckUserDataDTO(
    @SerializedName("accountName")
    val userId: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
)
