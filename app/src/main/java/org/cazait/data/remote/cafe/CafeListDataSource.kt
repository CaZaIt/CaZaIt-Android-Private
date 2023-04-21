package org.cazait.data.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes

interface CafeListDataSource {
    suspend fun getListCafes(): Resource<ListCafesRes>
    suspend fun getListFavorites(): Resource<ListFavoritesRes>
}