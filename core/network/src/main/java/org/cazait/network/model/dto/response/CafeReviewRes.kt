package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName
import org.cazait.model.CafeReview

data class CafeReviewRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val review: ReviewDTO
)

data class ReviewDTO(
    @SerializedName("reviewResponses") val reviews: List<CafeReview>,
    @SerializedName("totalElements") val total: Int,
    @SerializedName("nextCursor") val nextCursor: Long
)

data class CafeReviewPostRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val reviewResult: ReviewPostDTO
)

data class ReviewPostDTO(
    @SerializedName("reviewId")
    val reviewId: Long,
    @SerializedName("cafeId")
    val cafeId: Long,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdDate: String,
)
