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
import org.cazait.core.domain.model.cafe.CafeId
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.UserId
import javax.inject.Inject

class CafeInfoRemoteDataSource @Inject constructor(
    private val cafeApi: CafeApi,
    @Authenticated private val reviewApi: ReviewApi,
    @Authenticated private val favoriteApi: FavoriteApi,
) {
    suspend fun getCafe(
        cafeId: CafeId,
    ): NetworkResult<CafeResponse> = cafeApi.getCafe(cafeId = cafeId.toUUID())

    suspend fun getMenus(
        cafeId: CafeId,
    ): NetworkResult<CafeMenuResponse> =
        cafeApi.getMenus(cafeId = cafeId.toUUID())

    suspend fun getReviews(
        cafeId: CafeId,
        sortBy: String?,
        score: Int?,
        lastId: Long?,
    ): NetworkResult<CafeReviewResponse> = cafeApi.getReviews(
        cafeId = cafeId.toUUID(),
        sortBy = sortBy,
        score = score,
        lastId = lastId,
    )

    suspend fun postReview(
        userId: UserId,
        cafeId: CafeId,
        cafeReviewPostRequest: CafeReviewPostRequest,
    ): NetworkResult<CafeReviewPostResponse> =
        reviewApi.postReviewAuth(
            userId = userId.toUUID(),
            cafeId = cafeId.toUUID(),
            cafeReviewPostRequest = cafeReviewPostRequest,
        )

    suspend fun postFavoriteCafe(
        userId: UserId,
        cafeId: CafeId,
    ): NetworkResult<PostFavoriteCafeResponse> =
        favoriteApi.postFavoriteCafeAuth(
            userId = userId.toUUID(),
            cafeId = cafeId.toUUID(),
        )

    suspend fun deleteFavoriteCafe(
        userId: UserId,
        cafeId: CafeId,
    ): NetworkResult<DeleteFavoriteCafeResponse> =
        favoriteApi.deleteFavoriteCafeAuth(
            userId = userId.toUUID(),
            cafeId = cafeId.toUUID(),
        )
}
