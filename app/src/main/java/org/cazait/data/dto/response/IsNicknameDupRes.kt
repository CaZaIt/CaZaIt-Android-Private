package org.cazait.data.dto.response

import com.google.gson.annotations.SerializedName

data class IsNicknameDupRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String,
)
