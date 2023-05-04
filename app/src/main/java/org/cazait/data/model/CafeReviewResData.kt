package org.cazait.data.model

data class CafeReviewResData(
    val reviewRes: List<CafeReview>,
    val total: Int,
    val nextCursor: Int
)
