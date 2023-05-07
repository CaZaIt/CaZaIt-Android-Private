package org.cazait.data.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.dto.request.ListCafesReq

interface CafeListRemoteDataSource {
    suspend fun getListFavorites(userId: Long): Resource<ListFavoritesRes>
    suspend fun getListCafes(userId: Long, query: ListCafesReq): Resource<ListCafesRes>
    suspend fun getListCafesWithGuest(query: ListCafesReq): Resource<ListCafesRes>
}