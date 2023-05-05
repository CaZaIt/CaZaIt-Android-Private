package org.cazait.data.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.model.request.ListCafesReq
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes

interface CafeListRemoteDataSource {
    suspend fun getListFavorites(userId: Long): Resource<ListFavoritesRes>
    suspend fun getListCafes(userId: Long, query: ListCafesReq): Resource<ListCafesRes>
    suspend fun getListCafesWithGuest(query: ListCafesReq): Resource<ListCafesRes>
}