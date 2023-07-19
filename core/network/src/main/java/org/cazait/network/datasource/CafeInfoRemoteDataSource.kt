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
        userId: String,
        cafeId: Long,
        score: Int,
        content: String,
    ): DataResponse<CafeReviewPostRes>
    suspend fun postFavoriteCafeAuth(userId: String, cafeId: Long): DataResponse<PostFavoriteCafeRes>
    suspend fun deleteFavoriteCafeAuth(userId: String, cafeId: Long): DataResponse<DeleteFavoriteCafeRes>
}