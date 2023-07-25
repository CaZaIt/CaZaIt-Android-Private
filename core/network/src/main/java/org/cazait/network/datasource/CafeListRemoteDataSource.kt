package org.cazait.network.datasource

import org.cazait.network.model.dto.request.ListCafesReq
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.response.ListCafesRes
import org.cazait.network.model.dto.response.ListFavoritesRes
import org.cazait.network.model.dto.response.PostFavoriteCafeRes

interface CafeListRemoteDataSource {
    suspend fun getListFavoritesAuth(userId: String): DataResponse<ListFavoritesRes>
    suspend fun getListCafes(query: ListCafesReq): DataResponse<ListCafesRes>
    suspend fun postFavoriteCafe(userId: String, cafeId: Long): DataResponse<PostFavoriteCafeRes>
    suspend fun getCafeSearch(cafeName: String, query: ListCafesReq): DataResponse<ListCafesRes>
}