package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.cazait.model.CafeReview

@JsonClass(generateAdapter = true)
data class CafeReviewResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val review: ReviewDTO,
)

@JsonClass(generateAdapter = true)
data class ReviewDTO(
    @Json(name = "reviewResponses")
    val reviews: List<CafeReview>,
    @Json(name = "totalElements")
    val total: Int,
    @Json(name = "nextCursor")
    val nextCursor: Long,
)

@JsonClass(generateAdapter = true)
data class CafeReviewPostResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val reviewResult: ReviewPostDTO,
)

@JsonClass(generateAdapter = true)
data class ReviewPostDTO(
    @Json(name = "reviewId")
    val reviewId: Long,
    @Json(name = "cafeId")
    val cafeId: Long,
    @Json(name = "userId")
    val userId: String,
    @Json(name = "score")
    val score: Int,
    @Json(name = "content")
    val content: String,
    @Json(name = "createdAt")
    val createdDate: String,
)
