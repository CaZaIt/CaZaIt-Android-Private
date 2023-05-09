package org.cazait.data.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.model.response.CafeMenuRes
import org.cazait.data.model.response.CafeReviewRes

interface CafeInfoRemoteDataSource {
    suspend fun getMenus(cafeId: Long): Resource<CafeMenuRes>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Resource<CafeReviewRes>
}