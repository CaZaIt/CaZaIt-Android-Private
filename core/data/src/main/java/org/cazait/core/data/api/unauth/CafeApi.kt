package org.cazait.core.data.api.unauth

import org.cazait.core.data.datasource.response.CafeMenuResponse
import org.cazait.core.data.datasource.response.CafeResponse
import org.cazait.core.data.datasource.response.CafeReviewResponse
import org.cazait.core.data.datasource.response.ListCafesResponse
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface CafeApi {
    @GET("/api/cafes/{cafeId}")
    suspend fun getCafe(
        @Path("cafeId") cafeId: UUID,
    ): NetworkResult<CafeResponse>

    @GET("/api/cafes/all")
    suspend fun getListCafesWithGuest(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): NetworkResult<ListCafesResponse>

    @GET("/api/cafes/name/{cafeName}")
    suspend fun getCafeByNameWithGuest(
        @Path("cafeName") cafeName: String,
        @Query("latitude") latitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): NetworkResult<ListCafesResponse>

    @GET("/api/menus/cafe/{cafeId}")
    suspend fun getMenus(
        @Path("cafeId") cafeId: UUID,
    ): NetworkResult<CafeMenuResponse>

    @GET("/api/reviews/{cafeId}/all")
    suspend fun getReviews(
        @Path("cafeId") cafeId: UUID,
        @Query("sortBy") sortBy: String?,
        @Query("score") score: Int?,
        @Query("lastId") lastId: Long?,
    ): NetworkResult<CafeReviewResponse>

    @GET("/api/cafes/name/{cafeName}")
    suspend fun getCafeSearch(
        @Path("cafeName") cafeName: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("sort") sort: String,
        @Query("limit") limit: String,
    ): NetworkResult<ListCafesResponse>
}
