package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.request.CafeReviewPostRequest
import org.cazait.core.data.datasource.response.CafeReviewPostResponse
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface ReviewApi {
    @POST("/api/reviews/user/{userId}/cafe/{cafeId}")
    suspend fun postReviewAuth(
        @Path("userId") userId: UUID,
        @Path("cafeId") cafeId: UUID,
        @Body cafeReviewPostRequest: CafeReviewPostRequest,
    ): NetworkResult<CafeReviewPostResponse>
}
