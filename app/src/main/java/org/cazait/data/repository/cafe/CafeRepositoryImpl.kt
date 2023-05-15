package org.cazait.data.repository.cafe

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.data.Resource
import org.cazait.data.dto.request.ListCafesReq
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.dto.response.CafeMenuRes
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.data.dto.response.PostFavoriteCafeRes
import org.cazait.data.model.FavoriteCafe
import org.cazait.data.model.mapper.CafeMapper
import org.cazait.data.source.local.dao.CafeDAO
import org.cazait.data.source.remote.cafe.CafeInfoRemoteData
import org.cazait.data.source.remote.cafe.CafeListRemoteData
import org.cazait.domain.model.Cafe
import org.cazait.domain.repository.CafeRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CafeRepositoryImpl @Inject constructor(
    private val cafeListRemoteData: CafeListRemoteData,
    private val cafeInfoRemoteData: CafeInfoRemoteData,
    private val cafeDAO: CafeDAO,
    private val cafeMapper: CafeMapper,
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
        return flow {
            emit(cafeInfoRemoteData.getMenus(cafeId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Flow<Resource<CafeReviewRes>> {
        return flow {
            emit(cafeInfoRemoteData.getReviews(cafeId, sortBy, score, lastId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun insertFavoriteCafe(cafe: Cafe): Boolean {
        return try {
            cafeDAO.insertFavoriteCafe(cafeMapper.toFavoriteCafeEntity(cafe))
            true
        } catch (e: IOException) {
            false
        }
    }

    override suspend fun updateFavoriteCafe(cafe: Cafe): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavoriteCafes(): Flow<List<Cafe>> {
        return flow {
            cafeDAO.selectAllFavoriteCafe().collect { list ->
                emit(list.map { cafeMapper.toCafeFromFavoriteCafeEntity(it) })
            }
        }
    }

    override suspend fun postFavoriteCafe(
        userId: Long,
        cafe: Cafe
    ): Flow<Resource<PostFavoriteCafeRes>> {
        return flow {
            emit(cafeListRemoteData.postFavoriteCafe(userId, cafe.cafeId))
        }.flowOn(ioDispatcher)
    }
}