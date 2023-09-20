package org.cazait.model

data class CafeReviews(
    val reviews: List<CafeReview>,
    val total: Int,
    val isLast: Boolean,
)

data class CafeReview(
    val userId: String,
    val reviewId: String,
    val cafeName: String,
    val nickname: String,
    val score: Int,
    val content: String
)
