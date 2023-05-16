package org.cazait.data.dto.response

import com.google.gson.annotations.SerializedName

data class PostFavoriteCafeRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("data")
    val data: CafeId
)

data class CafeId(
    @SerializedName("id")
    val id: Long
)