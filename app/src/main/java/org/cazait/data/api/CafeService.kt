package org.cazait.data.api

import org.cazait.data.dto.response.CafeMenuRes
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.dto.response.PostFavoriteCafeRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CafeService {
    @GET("/api/cafes/all/user/{userId}")
    suspend fun getListCafes(
        @Path("userId") userId: Long,
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): Response<ListCafesRes>

    @GET("/api/favorites/user/{userId}")
    suspend fun getListFavorites(
        @Path("userId") userId: Long
    ): Response<ListFavoritesRes>

    @GET("api/cafes/all")
    suspend fun getListCafesWithGuest(
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): Response<ListCafesRes>

    @GET("api/cafes/name/{cafeName}")
    fun getCafeByNameWithGuest(
        @Path("cafeName") cafeName: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): Response<ListCafesRes>

    @GET("/api/menus/cafe/{cafeId}")
    fun getMenus(
        @Path("cafeId") cafeId: Long
    ): Call<CafeMenuRes>

    @GET("/api/reviews/{cafeId}/all")
    fun getReviews(
        @Path("cafeId") cafeId: Long,
        @Query("sortBy") sortBy: String?,
        @Query("score") score: Int?,
        @Query("lastId") lastId: Long?
    ): Call<CafeReviewRes>

    @POST("/api/favorites/user/{userId}/cafe/{cafeId}")
    suspend fun postFavoriteCafe(
        @Path("userId") userId: Long,
        @Path("cafeId") cafeId: Long,
    ): Response<PostFavoriteCafeRes>
}