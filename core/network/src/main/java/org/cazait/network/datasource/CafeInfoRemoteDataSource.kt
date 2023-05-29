package org.cazait.network.datasource

import android.os.Looper
import org.cazait.network.model.dto.response.CafeMenuRes
import org.cazait.network.model.dto.response.CafeReviewRes
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.response.CafeReviewPostRes

interface CafeInfoRemoteDataSource {
    suspend fun getMenus(cafeId: Long): DataResponse<CafeMenuRes>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): DataResponse<CafeReviewRes>

    suspend fun postReview(
        userId: Long,
        cafeId: Long,
        score: Int,
        content: String,
    ): DataResponse<CafeReviewPostRes>
}