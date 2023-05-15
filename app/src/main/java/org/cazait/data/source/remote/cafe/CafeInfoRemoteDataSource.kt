package org.cazait.data.source.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.dto.response.CafeMenuRes
import org.cazait.data.dto.response.CafeReviewRes

interface CafeInfoRemoteDataSource {
    suspend fun getMenus(cafeId: Long): Resource<CafeMenuRes>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Resource<CafeReviewRes>
}