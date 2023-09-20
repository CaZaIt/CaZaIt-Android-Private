package org.cazait.network.datasource

import org.cazait.network.NetworkConnectivity
import org.cazait.network.api.auth.FavoriteService
import org.cazait.network.api.unauth.CafeService
import org.cazait.network.api.auth.ReviewService
import org.cazait.network.di.Authenticated
import org.cazait.network.error.NETWORK_ERROR
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
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CafeInfoRemoteData @Inject constructor(
    private val cafeService: CafeService,
    @Authenticated private val reviewServiceAuth: ReviewService,
    @Authenticated private val favoriteServiceAuth: FavoriteService,
    private val networkConnectivity: NetworkConnectivity,
) : CafeInfoRemoteDataSource {
    override suspend fun getCafe(cafeId: String): DataResponse<CafeResTemp> {
        return when (val response = processCall {
            cafeService.getCafe(cafeId)
        }) {
            is CafeResTemp -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun getMenus(cafeId: String): DataResponse<CafeMenuRes> {
        return when (val response = processCall {
            cafeService.getMenus(
                cafeId = cafeId
            )
        }) {
            is CafeMenuRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun getReviews(
        cafeId: String,
        sortBy: String?,
        nums: Int,
        score: Int?
    ): DataResponse<CafeReviewRes> {
        return when (val response = processCall {
            cafeService.getReviews(
                cafeId, sortBy, nums, score
            )
        }) {
            is CafeReviewRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postReviewAuth(
        userId: String,
        cafeId: String,
        score: Int,
        content: String
    ): DataResponse<CafeReviewPostRes> {
        return when (val response = processCall {
            val req = CafeReviewPostReq(score, content)
            reviewServiceAuth.postReviewAuth(userId, cafeId, req)
        }) {
            is CafeReviewPostRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun patchReviewAuth(
        reviewId: String,
        body: CafeReviewPostReq
    ): DataResponse<CafeReviewPatchRes> {
        return when (val response =
            processCall { reviewServiceAuth.patchReviewAuth(reviewId, body) }) {
            is CafeReviewPatchRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    override suspend fun deleteReviewAuth(reviewId: String): DataResponse<ReviewDeleteRes> {
        return when (val response = processCall { reviewServiceAuth.deleteReviewAuth(reviewId) }) {
            is ReviewDeleteRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    override suspend fun postFavoriteCafeAuth(
        userId: String,
        cafeId: String
    ): DataResponse<PostFavoriteCafeRes> {
        return when (val response = processCall {
            favoriteServiceAuth.postFavoriteCafeAuth(
                userId,
                cafeId,
            )
        }) {
            is PostFavoriteCafeRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    override suspend fun deleteFavoriteCafeAuth(
        userId: String,
        cafeId: String,
    ): DataResponse<DeleteFavoriteCafeRes> {
        return when (val response = processCall {
            favoriteServiceAuth.deleteFavoriteCafeAuth(
                userId,
                cafeId,
            )
        }) {
            is DeleteFavoriteCafeRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    private suspend fun processCall(
        responseCall: suspend () -> Response<*>
    ): Any? {
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}