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
