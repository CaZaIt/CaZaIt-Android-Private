package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.response.DeleteFavoriteCafeResponse
import org.cazait.core.data.datasource.response.ListFavoritesResponse
import org.cazait.core.data.datasource.response.PostFavoriteCafeResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {
    @POST("/api/favorites/user/{userId}/cafe/{cafeId}")
    suspend fun postFavoriteCafeAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: String,
    ): Response<PostFavoriteCafeResponse>

    @DELETE("/api/favorites/delete/{userId}/{cafeId}")
    suspend fun deleteFavoriteCafeAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: String,
    ): Response<DeleteFavoriteCafeResponse>

    @GET("/api/favorites/user/{userId}")
    suspend fun getListFavoritesAuth(
        @Path("userId") userId: String,
    ): Response<ListFavoritesResponse>
}
