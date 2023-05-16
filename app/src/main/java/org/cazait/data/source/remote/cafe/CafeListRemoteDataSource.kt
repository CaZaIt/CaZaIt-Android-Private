package org.cazait.data.source.remote.cafe

import org.cazait.data.dto.request.ListCafesReq
import org.cazait.data.dto.response.DataResponse
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.dto.response.PostFavoriteCafeRes

interface CafeListRemoteDataSource {
    suspend fun getListFavorites(userId: Long): DataResponse<ListFavoritesRes>
    suspend fun getListCafes(userId: Long, query: ListCafesReq): DataResponse<ListCafesRes>
    suspend fun getListCafesWithGuest(query: ListCafesReq): DataResponse<ListCafesRes>
    suspend fun postFavoriteCafe(userId: Long, cafeId: Long): DataResponse<PostFavoriteCafeRes>
}