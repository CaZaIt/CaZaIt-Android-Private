package org.cazait.model

data class CafeReviews(
    val reviews: List<CafeReview>,
    val total: Int,
    val nextCursor: Long,
)

data class CafeReview(
    val userId: String,
    val cafeId: Long,
    val score: Int,
    val content: String
)
