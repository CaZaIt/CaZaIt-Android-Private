package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.request.CafeReviewPostRequest
import org.cazait.core.data.datasource.response.CafeReviewPostResponse
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @POST("/api/reviews/user/{userId}/cafe/{cafeId}")
    suspend fun postReviewAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: String,
        @Body cafeReviewPostRequest: CafeReviewPostRequest,
    ): NetworkResult<CafeReviewPostResponse>
}
