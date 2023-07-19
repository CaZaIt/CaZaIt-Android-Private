package org.cazait.network.api.unauth

import org.cazait.network.model.dto.response.CafeMenuRes
import org.cazait.network.model.dto.response.CafeResTemp
import org.cazait.network.model.dto.response.CafeReviewRes
import org.cazait.network.model.dto.response.ListCafesRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CafeService {
    @GET("/api/cafes/id/{cafeId}")
    suspend fun getCafe(
        @Path("cafeId") cafeId: Long,
    ): Response<CafeResTemp>

    @GET("/api/cafes/all/user/{userId}")
    suspend fun getListCafes(
        @Path("userId") userId: String,
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): Response<ListCafesRes>

    @GET("api/cafes/all")
    suspend fun getListCafesWithGuest(
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): Response<ListCafesRes>

    @GET("api/cafes/name/{cafeName}")
    suspend fun getCafeByNameWithGuest(
        @Path("cafeName") cafeName: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): Response<ListCafesRes>

    @GET("/api/menus/cafe/{cafeId}")
    suspend fun getMenus(
        @Path("cafeId") cafeId: Long
    ): Response<CafeMenuRes>

    @GET("/api/reviews/{cafeId}/all")
    suspend fun getReviews(
        @Path("cafeId") cafeId: Long,
        @Query("sortBy") sortBy: String?,
        @Query("score") score: Int?,
        @Query("lastId") lastId: Long?
    ): Response<CafeReviewRes>

    @GET("/api/cafes/name/{cafeName}")
    suspend fun getCafeSearch(
        @Path("cafeName") cafeName: String,
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String
    ): Response<ListCafesRes>
}