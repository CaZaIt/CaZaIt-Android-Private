package org.cazait.network.api.auth

import org.cazait.network.model.dto.request.CafeReviewPostReq
import org.cazait.network.model.dto.response.CafeReviewPatchRes
import org.cazait.network.model.dto.response.CafeReviewPostRes
import org.cazait.network.model.dto.response.ReviewDeleteRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @POST("/api/reviews/user/{userId}/cafe/{cafeId}")
    suspend fun postReviewAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: String,
        @Body reviewPostReq: CafeReviewPostReq,
    ): Response<CafeReviewPostRes>

    @PATCH("/api/reviews/edit/{reviewId}")
    suspend fun patchReviewAuth(
        @Path("reviewId") reviewId: String,
        @Body reviewPostReq: CafeReviewPostReq,
    ): Response<CafeReviewPatchRes>

    @DELETE("/api/reviews/{reviewId}")
    suspend fun deleteReviewAuth(
        @Path("reviewId") reviewId: String
    ): Response<ReviewDeleteRes>
}