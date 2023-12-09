package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.response.CafeReviewPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @POST("/api/reviews/user/{userId}/cafe/{cafeId}")
    suspend fun postReviewAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: String,
        @Body reviewPostReq: org.cazait.core.data.datasource.request.CafeReviewPostRequest,
    ): Response<CafeReviewPostResponse>
}
