package org.cazait.data.dto.request

import com.google.gson.annotations.SerializedName

data class CafeReviewReq(
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("sortBy") val sortBy: String,
    @SerializedName("score") val score: Int,
    @SerializedName("lastId") val lastId: Long
)
