package org.cazait.network.api.auth

import org.cazait.network.model.dto.response.RefreshTokenRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TokenService {
    @GET("/api/auths/refresh")
    suspend fun getRefreshTokenAuth(
        @Query("role")
        role: String = "user",
        @Header("Refresh-Token")
        refreshToken: String,
    ): Response<RefreshTokenRes>
}