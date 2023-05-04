package org.cazait.data.repository.cafe

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.data.Resource
import org.cazait.data.model.ListItem
import org.cazait.data.model.request.ListCafesReq
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes
import org.cazait.data.remote.cafe.CafeListRemoteData
import org.cazait.data.remote.cafe.CafeListRemoteDataSource
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CafeRepositoryImpl @Inject constructor(
    private val cafeListRemoteData: CafeListRemoteData,
    private val ioDispatcher: CoroutineContext,
) : CafeRepository {
    override suspend fun getListCafes(
        userId: Long,
        latitude: String,
        longitude: String,
        sort: String,
        limit: String,
    ): Flow<Resource<ListCafesRes>> {
        val query =
            ListCafesReq(latitude = latitude, longitude = longitude, sort = sort, limit = limit)

        return flow {
            emit(cafeListRemoteData.getListCafes(userId, query))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getListFavorites(userId: Long): Flow<Resource<ListFavoritesRes>> {
        return flow {
            emit(cafeListRemoteData.getListFavorites(userId))
        }.flowOn(ioDispatcher)
    }
}