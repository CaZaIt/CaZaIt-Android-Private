package org.cazait.network.model.dto.request

data class CafeReviewReq(
    val cafeId: Long,
    val sortBy: String,
    val score: Int,
    val lastId: Long
)
