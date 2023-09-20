package org.cazait.network.datasource

import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.CafeReviewPostReq
import org.cazait.network.model.dto.response.CafeMenuRes
import org.cazait.network.model.dto.response.CafeResTemp
import org.cazait.network.model.dto.response.CafeReviewPatchRes
import org.cazait.network.model.dto.response.CafeReviewPostRes
import org.cazait.network.model.dto.response.CafeReviewRes
import org.cazait.network.model.dto.response.DeleteFavoriteCafeRes
import org.cazait.network.model.dto.response.PostFavoriteCafeRes
import org.cazait.network.model.dto.response.ReviewDeleteRes

interface CafeInfoRemoteDataSource {
    suspend fun getCafe(cafeId: String): DataResponse<CafeResTemp>

    suspend fun getMenus(cafeId: String): DataResponse<CafeMenuRes>
    suspend fun getReviews(
        cafeId: String,
        sortBy: String?,
        nums: Int,
        score: Int?
    ): DataResponse<CafeReviewRes>

    suspend fun postReviewAuth(
        userId: String,
        cafeId: String,
        score: Int,
        content: String,
    ): DataResponse<CafeReviewPostRes>
    suspend fun patchReviewAuth(reviewId: String, body: CafeReviewPostReq): DataResponse<CafeReviewPatchRes>
    suspend fun deleteReviewAuth(reviewId: String): DataResponse<ReviewDeleteRes>
    suspend fun postFavoriteCafeAuth(userId: String, cafeId: String): DataResponse<PostFavoriteCafeRes>
    suspend fun deleteFavoriteCafeAuth(userId: String, cafeId: String): DataResponse<DeleteFavoriteCafeRes>
}