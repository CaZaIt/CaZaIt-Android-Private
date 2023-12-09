package org.cazait.core.data.datasource.request

data class CafeReviewRequest(
    val cafeId: Long,
    val sortBy: String,
    val score: Int,
    val lastId: Long,
)

data class CafeReviewPostRequest(
    val score: Int,
    val content: String,
)
