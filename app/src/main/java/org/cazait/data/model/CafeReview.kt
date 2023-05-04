package org.cazait.data.model

import com.google.gson.annotations.SerializedName

data class CafeReview(
    @SerializedName("userId") val userId: Int,
    @SerializedName("cafeId") val cafeId: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("content") val content: String
)
