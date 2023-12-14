package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.response.DeleteFavoriteCafeResponse
import org.cazait.core.data.datasource.response.ListFavoritesResponse
import org.cazait.core.data.datasource.response.PostFavoriteCafeResponse
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface FavoriteApi {
    @POST("/api/favorites/user/{userId}/cafe/{cafeId}")
    suspend fun postFavoriteCafeAuth(
        @Path("userId") userId: UUID,
        @Path("cafeId") cafeId: UUID,
    ): NetworkResult<PostFavoriteCafeResponse>

    @DELETE("/api/favorites/delete/{userId}/{cafeId}")
    suspend fun deleteFavoriteCafeAuth(
        @Path("userId") userId: UUID,
        @Path("cafeId") cafeId: UUID,
    ): NetworkResult<DeleteFavoriteCafeResponse>

    @GET("/api/favorites/user/{userId}")
    suspend fun getListFavoritesAuth(
        @Path("userId") userId: UUID,
    ): NetworkResult<ListFavoritesResponse>
}
