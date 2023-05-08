package org.cazait.data.model.response

import com.google.gson.annotations.SerializedName
import org.cazait.data.model.CafeReview

data class CafeReviewRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ReviewData
)

data class ReviewData(
    @SerializedName("reviewResponses") val reviewRes: List<CafeReview>,
    @SerializedName("totalElements") val total: Int,
    @SerializedName("nextCursor") val nextCursor: Long
)
