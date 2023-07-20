package org.cazait.network.api.auth

import org.cazait.network.model.dto.response.ListCafesRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CafeAuthService {
    @GET("/api/cafes/all/user/{userId}")
    suspend fun getListCafes(
        @Path("userId") userId: String,
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): Response<ListCafesRes>
}