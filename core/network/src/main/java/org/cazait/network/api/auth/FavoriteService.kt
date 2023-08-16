package org.cazait.network.api.auth

import org.cazait.network.model.dto.response.DeleteFavoriteCafeRes
import org.cazait.network.model.dto.response.ListFavoritesRes
import org.cazait.network.model.dto.response.PostFavoriteCafeRes
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteService {
    @POST("/api/favorites/user/{userId}/cafe/{cafeId}")
    suspend fun postFavoriteCafeAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: String,
    ): Response<PostFavoriteCafeRes>

    @DELETE("/api/favorites/delete/{userId}/{cafeId}")
    suspend fun deleteFavoriteCafeAuth(
        @Path("userId") userId: String,
        @Path("cafeId") cafeId: String,
    ): Response<DeleteFavoriteCafeRes>

    @GET("/api/favorites/user/{userId}")
    suspend fun getListFavoritesAuth(
        @Path("userId") userId: String
    ): Response<ListFavoritesRes>
}