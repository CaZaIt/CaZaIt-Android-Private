package org.cazait.core.data.datasource

import org.cazait.core.data.api.auth.FavoriteApi
import org.cazait.core.data.api.auth.ReviewApi
import org.cazait.core.data.api.unauth.CafeApi
import org.cazait.core.data.datasource.request.CafeReviewPostRequest
import org.cazait.core.data.datasource.response.CafeMenuResponse
import org.cazait.core.data.datasource.response.CafeResponse
import org.cazait.core.data.datasource.response.CafeReviewPostResponse
import org.cazait.core.data.datasource.response.CafeReviewResponse
import org.cazait.core.data.datasource.response.DeleteFavoriteCafeResponse
import org.cazait.core.data.datasource.response.PostFavoriteCafeResponse
import org.cazait.core.data.di.Authenticated
import org.cazait.core.domain.model.network.NetworkResult
import javax.inject.Inject

class CafeInfoRemoteDataSource @Inject constructor(
    private val cafeApi: CafeApi,
    @Authenticated private val reviewApi: ReviewApi,
    @Authenticated private val favoriteApi: FavoriteApi,
) {
    suspend fun getCafe(
        cafeId: String,
    ): NetworkResult<CafeResponse> = cafeApi.getCafe(cafeId = cafeId)

    suspend fun getMenus(
        cafeId: String,
    ): NetworkResult<CafeMenuResponse> =
        cafeApi.getMenus(cafeId = cafeId)

    suspend fun getReviews(
        cafeId: String,
        sortBy: String?,
        score: Int?,
        lastId: Long?,
    ): NetworkResult<CafeReviewResponse> = cafeApi.getReviews(
        cafeId = cafeId,
        sortBy = sortBy,
        score = score,
        lastId = lastId,
    )

    suspend fun postReview(
        userId: String,
        cafeId: String,
        cafeReviewPostRequest: CafeReviewPostRequest,
    ): NetworkResult<CafeReviewPostResponse> =
        reviewApi.postReviewAuth(
            userId = userId,
            cafeId = cafeId,
            cafeReviewPostRequest = cafeReviewPostRequest,
        )

    suspend fun postFavoriteCafe(
        userId: String,
        cafeId: String,
    ): NetworkResult<PostFavoriteCafeResponse> =
        favoriteApi.postFavoriteCafeAuth(
            userId = userId,
            cafeId = cafeId,
        )

    suspend fun deleteFavoriteCafe(
        userId: String,
        cafeId: String,
    ): NetworkResult<DeleteFavoriteCafeResponse> =
        favoriteApi.deleteFavoriteCafeAuth(
            userId = userId,
            cafeId = cafeId,
        )
}
