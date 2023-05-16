package org.cazait.data.repository.cafe

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.domain.model.Resource
import org.cazait.data.dto.request.ListCafesReq
import org.cazait.data.dto.response.CafeMenuRes
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.data.dto.response.DataResponse
import org.cazait.domain.model.FavoriteCafe
import org.cazait.data.model.mapper.DataMapper.toCafe
import org.cazait.data.model.mapper.DataMapper.toFavoriteCafe
import org.cazait.data.model.mapper.DataMapper.toFavoriteCafeEntity
import org.cazait.data.source.local.dao.CafeDAO
import org.cazait.data.source.remote.cafe.CafeInfoRemoteData
import org.cazait.data.source.remote.cafe.CafeListRemoteData
import org.cazait.domain.model.Cafes
import org.cazait.domain.model.FavoriteCafes
import org.cazait.domain.repository.CafeRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CafeRepositoryImpl @Inject constructor(
    private val cafeListRemoteData: CafeListRemoteData,
    private val cafeInfoRemoteData: CafeInfoRemoteData,
    private val cafeDAO: CafeDAO,
    private val ioDispatcher: CoroutineContext,
) : CafeRepository {
    override suspend fun getListCafes(
        userId: Long?,
        latitude: String,
        longitude: String,
        sort: String,
        limit: String,
    ): Flow<Resource<Cafes>> {
        val query =
            ListCafesReq(latitude = latitude, longitude = longitude, sort = sort, limit = limit)

        return flow {
            val response = if (userId == null) {
                cafeListRemoteData.getListCafesWithGuest(query)
            } else {
                cafeListRemoteData.getListCafes(userId, query)
            }
            when (response) {
                is DataResponse.Success -> {
                    response.data?.cafes?.forEach { list ->
                        val cafes = list.map { it.toCafe() }
                        emit(Resource.Success(Cafes(cafes)))
                    } ?: emit(Resource.Success(Cafes(emptyList())))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getListFavorites(userId: Long): Flow<Resource<FavoriteCafes>> {
        return flow {
            when (val response = cafeListRemoteData.getListFavorites(userId)) {
                is DataResponse.Success -> {
                    val fc = FavoriteCafes(
                        response.data?.favorites?.map {
                            it.toFavoriteCafe()
                        }.orEmpty()
                    )
                    emit(Resource.Success(fc))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
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

    override suspend fun insertFavoriteCafe(cafe: FavoriteCafe): Boolean {
        return try {
            cafeDAO.insertFavoriteCafe(cafe.toFavoriteCafeEntity())
            true
        } catch (e: IOException) {
            false
        }
    }

    override suspend fun updateFavoriteCafe(cafe: FavoriteCafe): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavoriteCafes(): Flow<Resource<FavoriteCafes>> {
        return flow {
            cafeDAO.selectAllFavoriteCafe().collect { list ->
                emit(Resource.Success(FavoriteCafes(list.map { it.toFavoriteCafe() })))
            }
        }
    }

    override suspend fun postFavoriteCafe(
        userId: Long,
        cafe: FavoriteCafe
    ): Boolean {
        return cafeListRemoteData.postFavoriteCafe(userId, cafe.cafeId) is DataResponse.Success
    }
}