package org.cazait.data.api

import org.cazait.data.dto.request.SignInReq
import org.cazait.data.dto.response.RefreshTokenRes
import org.cazait.data.dto.response.SignInRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    @POST("/api/auths/log-in")
    fun postSignIn(
        @Query("role") role: String = "user",
        @Body signInReq: SignInReq,
    ): Call<SignInRes>

    @GET("api/auths/{userId}")
    fun postRefreshToken(
        @Path("userId") userId: Long,
        @Query("role") role: String = "user",
        // 헤더맵
    ): Call<RefreshTokenRes>
}
