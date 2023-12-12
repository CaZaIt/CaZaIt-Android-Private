package org.cazait.core.data.datasource

import org.cazait.core.data.api.auth.FavoriteApi
import org.cazait.core.data.api.unauth.CafeApi
import org.cazait.core.data.datasource.response.ListCafesResponse
import org.cazait.core.data.datasource.response.ListFavoritesResponse
import org.cazait.core.data.di.Authenticated
import org.cazait.core.domain.model.network.NetworkResult
import javax.inject.Inject

class CafeListRemoteDataSource @Inject constructor(
    private val cafeApi: CafeApi,
    @Authenticated private val favoriteCafeApi: FavoriteApi,
) {
    suspend fun getListCafes(
        longitude: String,
        latitude: String,
        sort: String,
        limit: String,
    ): NetworkResult<ListCafesResponse> =
        cafeApi.getListCafesWithGuest(
            longitude = longitude,
            latitude = latitude,
            sort = sort,
            limit = limit,
        )

    suspend fun getListFavoritesAuth(
        userId: String,
    ): NetworkResult<ListFavoritesResponse> =
        favoriteCafeApi.getListFavoritesAuth(
            userId = userId,
        )

    suspend fun getCafeSearch(
        cafeName: String,
        latitude: String,
        longitude: String,
        sort: String,
        limit: String,
    ): NetworkResult<ListCafesResponse> = cafeApi.getCafeSearch(
        cafeName = cafeName,
        latitude = latitude,
        longitude = longitude,
        sort = sort,
        limit = limit,
    )
}
