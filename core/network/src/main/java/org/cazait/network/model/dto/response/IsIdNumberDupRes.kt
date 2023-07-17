package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class IsEmailDupRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String?,
)
