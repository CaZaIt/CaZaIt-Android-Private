package org.cazait.network.api.auth

import org.cazait.network.model.dto.request.CafeReviewPostReq
import org.cazait.network.model.dto.response.CafeReviewPostRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthedService {
    @POST("/api/reviews/user/{userId}/cafe/{cafeId}")
    suspend fun postReviewAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: Long,
        @Body reviewPostReq: CafeReviewPostReq,
    ): Response<CafeReviewPostRes>
}