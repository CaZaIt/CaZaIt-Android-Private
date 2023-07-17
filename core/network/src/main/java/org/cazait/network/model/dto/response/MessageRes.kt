package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class MessageRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: RequestData
)

data class RequestData(
    @SerializedName("recipientPhoneNumber")
    val recipientPhoneNumber: String,
    @SerializedName("requestTime")
    val requestTime: String
)
