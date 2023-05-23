package org.cazait.network.api

import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    @POST("/api/auths/log-in")
    suspend fun postSignIn(
        @Query("role") role: String = "user",
        @Body signInReq: SignInReq,
    ): Response<SignInRes>

    @GET("api/auths/{userId}")
    suspend fun postRefreshToken(
        @Path("userId") userId: Long,
        @Query("role") role: String = "user",
        // 헤더맵
    ): Response<RefreshTokenRes>
}
