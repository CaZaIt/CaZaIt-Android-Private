package org.cazait.network.datasource

import org.cazait.network.model.dto.request.ListCafesReq
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.response.ListCafesRes
import org.cazait.network.model.dto.response.ListFavoritesRes
import org.cazait.network.model.dto.response.PostFavoriteCafeRes

interface CafeListRemoteDataSource {
    suspend fun getListFavorites(userId: Long): DataResponse<ListFavoritesRes>
    suspend fun getListCafes(userId: Long, query: ListCafesReq): DataResponse<ListCafesRes>
    suspend fun getListCafesWithGuest(query: ListCafesReq): DataResponse<ListCafesRes>
    suspend fun postFavoriteCafe(userId: Long, cafeId: Long): DataResponse<PostFavoriteCafeRes>
}