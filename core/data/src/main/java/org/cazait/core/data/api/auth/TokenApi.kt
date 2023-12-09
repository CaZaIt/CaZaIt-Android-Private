package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.response.RefreshTokenResponse
import org.cazait.core.data.model.network.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TokenApi {
    @GET("/api/auths/refresh")
    suspend fun getRefreshTokenAuth(
        @Query("role")
        role: String = "user",
        @Header("Refresh-Token")
        refreshToken: String,
    ): NetworkResult<RefreshTokenResponse>
}
