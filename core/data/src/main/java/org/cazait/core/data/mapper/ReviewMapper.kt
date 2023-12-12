package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.CafeReviewPostResponse
import org.cazait.core.data.datasource.response.CafeReviewResponse
import org.cazait.core.data.datasource.response.ReviewDTO
import org.cazait.core.model.CafeReview
import org.cazait.core.model.CafeReviews

internal fun CafeReviewResponse.toData(): CafeReviews = CafeReviews(
    reviews = this.reviews.reviews.map(ReviewDTO::toData),
    total = this.reviews.total,
    nextCursor = this.reviews.nextCursor,
)

internal fun ReviewDTO.toData(): CafeReview = CafeReview(
    userId = this.userId,
    cafeId = this.cafeId,
    score = this.score,
    content = this.content,
)

internal fun CafeReviewPostResponse.toData(): String = this.reviewResult.createdDate
