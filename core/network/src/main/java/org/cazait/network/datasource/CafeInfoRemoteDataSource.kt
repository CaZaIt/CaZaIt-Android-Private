package org.cazait.network.datasource

import org.cazait.network.model.dto.response.CafeMenuRes
import org.cazait.network.model.dto.response.CafeReviewRes
import org.cazait.network.model.dto.DataResponse

interface CafeInfoRemoteDataSource {
    suspend fun getMenus(cafeId: Long): DataResponse<CafeMenuRes>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): DataResponse<CafeReviewRes>
}