package org.cazait.data.repository.cafe

import kotlinx.coroutines.flow.Flow
import org.cazait.data.Resource
import org.cazait.data.model.request.ListCafesReq
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes

interface CafeRepository {
    suspend fun getListCafes(userId: Long, query: ListCafesReq): Flow<Resource<ListCafesRes>>
    suspend fun getListFavorites(userId: Long): Flow<Resource<ListFavoritesRes>>
}