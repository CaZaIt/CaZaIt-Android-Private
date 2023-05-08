package org.cazait.data.model

import com.google.gson.annotations.SerializedName

data class CafeReview(
    @SerializedName("userId") val userId: Long,
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("score") val score: Int,
    @SerializedName("content") val content: String
)
