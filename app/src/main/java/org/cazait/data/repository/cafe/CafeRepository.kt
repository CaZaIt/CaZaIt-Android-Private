package org.cazait.data.repository.cafe

import kotlinx.coroutines.flow.Flow
import org.cazait.data.Resource
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.model.response.CafeMenuRes

interface CafeRepository {
    suspend fun getListFavorites(userId: Long): Flow<Resource<ListFavoritesRes>>
    suspend fun getListCafes(
        userId: Long?,
        latitude: String,
        longitude: String,
        sort: String = "distance",
        limit: String = "0"
    ): Flow<Resource<ListCafesRes>>

    suspend fun getMenus(cafeId: Long): Flow<Resource<CafeMenuRes>>
}