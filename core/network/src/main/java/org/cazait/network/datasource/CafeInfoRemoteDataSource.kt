package org.cazait.network.datasource

import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.response.CafeMenuRes
import org.cazait.network.model.dto.response.CafeResTemp
import org.cazait.network.model.dto.response.CafeReviewPostRes
import org.cazait.network.model.dto.response.CafeReviewRes
import org.cazait.network.model.dto.response.DeleteFavoriteCafeRes
import org.cazait.network.model.dto.response.PostFavoriteCafeRes

interface CafeInfoRemoteDataSource {
    suspend fun getCafe(cafeId: Long): DataResponse<CafeResTemp>

    suspend fun getMenus(cafeId: Long): DataResponse<CafeMenuRes>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): DataResponse<CafeReviewRes>

    suspend fun postReviewAuth(
        userId: Long,
        cafeId: Long,
        score: Int,
        content: String,
    ): DataResponse<CafeReviewPostRes>

    suspend fun postFavoriteCafe(userId: Long, cafeId: Long): DataResponse<PostFavoriteCafeRes>
    suspend fun deleteFavoriteCafe(userId: Long, cafeId: Long): DataResponse<DeleteFavoriteCafeRes>
}