package org.cazait.data.repository.cafe

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.data.Resource
import org.cazait.data.dto.request.ListCafesReq
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.model.response.CafeMenuRes
import org.cazait.data.remote.cafe.CafeInfoRemoteData
import org.cazait.data.remote.cafe.CafeListRemoteData
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CafeRepositoryImpl @Inject constructor(
    private val cafeListRemoteData: CafeListRemoteData,
    private val cafeInfoRemoteData: CafeInfoRemoteData,
    private val ioDispatcher: CoroutineContext,
) : CafeRepository {
    override suspend fun getListCafes(
        userId: Long?,
        latitude: String,
        longitude: String,
        sort: String,
        limit: String,
    ): Flow<Resource<ListCafesRes>> {
        val query =
            ListCafesReq(latitude = latitude, longitude = longitude, sort = sort, limit = limit)

        return flow {
            if (userId == null) {
                emit(cafeListRemoteData.getListCafesWithGuest(query))
            } else {
                emit(cafeListRemoteData.getListCafes(userId, query))
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getListFavorites(userId: Long): Flow<Resource<ListFavoritesRes>> {
        return flow {
            emit(cafeListRemoteData.getListFavorites(userId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMenus(cafeId: Long): Flow<Resource<CafeMenuRes>> {
        return flow{
            emit(cafeInfoRemoteData.getMenus(cafeId))
        }.flowOn(ioDispatcher)
    }
}