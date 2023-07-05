package org.cazait.network.api

import org.cazait.network.model.dto.request.CafeReviewPostReq
import org.cazait.network.model.dto.response.CafeId
import org.cazait.network.model.dto.response.CafeMenuRes
import org.cazait.network.model.dto.response.CafeRes
import org.cazait.network.model.dto.response.CafeResTemp
import org.cazait.network.model.dto.response.CafeReviewPostRes
import org.cazait.network.model.dto.response.CafeReviewRes
import org.cazait.network.model.dto.response.DeleteFavoriteCafeRes
import org.cazait.network.model.dto.response.ListCafesRes
import org.cazait.network.model.dto.response.ListFavoritesRes
import org.cazait.network.model.dto.response.PostFavoriteCafeRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CafeService {
    @GET("/api/cafes/id/{cafeId}")
    suspend fun getCafe(
        @Path("cafeId") cafeId: Long,
    ): Response<CafeResTemp>

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

    @POST("/api/favorites/user/{userId}/cafe/{cafeId}")
    suspend fun postFavoriteCafe(
        @Path("userId") userId: Long,
        @Path("cafeId") cafeId: Long,
    ): Response<PostFavoriteCafeRes>

    @DELETE("/api/favorites/delete/{userId}/{cafeId}")
    suspend fun deleteFavoriteCafe(
        @Path("userId") userId: Long,
        @Path("cafeId") cafeId: Long,
    ): Response<DeleteFavoriteCafeRes>

//    @POST("/api/reviews/user/{userId}/cafe/{cafeId}")
//    suspend fun postReviewAuth(
//        @Path("userId") userId: Long,
//        @Path("cafeId") cafeId: Long,
//        @Body reviewPostReq: CafeReviewPostReq,
//    ): Response<CafeReviewPostRes>

    @GET("/api/cafes/name/{cafeName}")
    suspend fun getCafeSearch(
        @Path("cafeName") cafeName: String,
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String
    ): Response<ListCafesRes>
}