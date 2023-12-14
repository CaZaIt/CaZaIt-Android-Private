package org.cazait.core.data.datasource

import org.cazait.core.data.api.auth.FavoriteApi
import org.cazait.core.data.api.unauth.CafeApi
import org.cazait.core.data.datasource.response.ListCafesResponse
import org.cazait.core.data.datasource.response.ListFavoritesResponse
import org.cazait.core.data.di.Authenticated
import org.cazait.core.domain.model.cafe.CafeName
import org.cazait.core.domain.model.cafe.Limit
import org.cazait.core.domain.model.cafe.Sort
import org.cazait.core.domain.model.location.Latitude
import org.cazait.core.domain.model.location.Longitude
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.UserId
import javax.inject.Inject

class CafeListRemoteDataSource @Inject constructor(
    private val cafeApi: CafeApi,
    @Authenticated private val favoriteCafeApi: FavoriteApi,
) {
    suspend fun getListCafes(
        longitude: Longitude,
        latitude: Latitude,
        sort: Sort,
        limit: Limit,
    ): NetworkResult<ListCafesResponse> =
        cafeApi.getListCafesWithGuest(
            longitude = longitude.toString(),
            latitude = latitude.toString(),
            sort = sort.toString(),
            limit = limit.toString(),
        )

    suspend fun getListFavoritesAuth(
        userId: UserId,
    ): NetworkResult<ListFavoritesResponse> =
        favoriteCafeApi.getListFavoritesAuth(
            userId = userId.toUUID(),
        )

    suspend fun getCafeSearch(
        cafeName: CafeName,
        latitude: Latitude,
        longitude: Longitude,
        sort: Sort,
        limit: Limit,
    ): NetworkResult<ListCafesResponse> = cafeApi.getCafeSearch(
        cafeName = cafeName.toString(),
        latitude = latitude.toString(),
        longitude = longitude.toString(),
        sort = sort.toString(),
        limit = limit.toString(),
    )
}
