package org.cazait.network.model.dto.request

import com.google.gson.annotations.SerializedName

data class CafeReviewReq(
    val cafeId: Long,
    val sortBy: String,
    val score: Int,
    val lastId: Long
)

data class CafeReviewPostReq(
    @SerializedName("score")
    val score: Int,
    @SerializedName("score")
    val content: String,
)
