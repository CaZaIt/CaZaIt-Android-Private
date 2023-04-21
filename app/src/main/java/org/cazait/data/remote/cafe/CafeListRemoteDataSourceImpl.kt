package org.cazait.data.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.api.CafeService
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes
import javax.inject.Inject

class CafeListRemoteDataSourceImpl @Inject constructor(
    val cafeService: CafeService,
) : CafeListDataSource {
    override suspend fun getListCafes(): Resource<ListCafesRes> {
        TODO("Not yet implemented")
    }

    override suspend fun getListFavorites(): Resource<ListFavoritesRes> {
        TODO("Not yet implemented")
    }

}