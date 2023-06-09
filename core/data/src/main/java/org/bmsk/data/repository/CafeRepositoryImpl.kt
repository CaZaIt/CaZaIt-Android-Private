package org.bmsk.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.bmsk.data.model.toCafe
import org.bmsk.data.model.toCafeMenu
import org.bmsk.data.model.toCafeReviews
import org.bmsk.data.model.toFavoriteCafe
import org.bmsk.data.model.toFavoriteCafeEntity
import org.bmsk.data.model.toRecentlyViewedCafeEntity
import org.cazait.database.dao.CafeDAO
import org.cazait.database.dao.RecentlyViewedCafeDAO
import org.cazait.model.Cafe
import org.cazait.model.CafeMenus
import org.cazait.model.CafeReviews
import org.cazait.model.Cafes
import org.cazait.model.FavoriteCafe
import org.cazait.model.FavoriteCafes
import org.cazait.model.Resource
import org.cazait.network.datasource.CafeInfoRemoteData
import org.cazait.network.datasource.CafeListRemoteData
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.ListCafesReq
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CafeRepositoryImpl @Inject constructor(
    private val cafeListRemoteData: CafeListRemoteData,
    private val cafeInfoRemoteData: CafeInfoRemoteData,
    private val cafeDAO: CafeDAO,
    private val recentlyViewedCafeDAO: RecentlyViewedCafeDAO,
    private val ioDispatcher: CoroutineContext,
) : CafeRepository {

    override suspend fun getCafeById(cafeId: Long): Flow<Resource<Cafe>> {
        return flow {
            when (val response = cafeInfoRemoteData.getCafe(cafeId)) {
                is DataResponse.Success -> {
                    response.data?.cafe?.let {
                        emit(Resource.Success(it.toCafe()))
                    }
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }
    }

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

    override suspend fun getMenus(cafeId: Long): Flow<Resource<CafeMenus>> {
        return flow {
            when (val response = cafeInfoRemoteData.getMenus(cafeId)) {
                is DataResponse.Success -> {
                    val cafeMenus = CafeMenus(
                        menus = response.data?.menus?.map {
                            it.toCafeMenu()
                        }.orEmpty()
                    )
                    emit(Resource.Success(cafeMenus))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Flow<Resource<CafeReviews>> {
        return flow {
            when (val response = cafeInfoRemoteData.getReviews(cafeId, sortBy, score, lastId)) {
                is DataResponse.Success -> {
                    val reviews =
                        response.data?.review?.toCafeReviews() ?: CafeReviews(emptyList(), 0, 0L)
                    emit(Resource.Success(reviews))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun postReview(
        userId: Long,
        cafeId: Long,
        score: Int,
        content: String
    ): Flow<Resource<String>> {
        return flow {
            when (val response = cafeInfoRemoteData.postReview(userId, cafeId, score, content)) {
                is DataResponse.Success -> {
                    val message: String =
                        response.data?.message ?: ""
                    Log.d("CafeRepository", "등록 성공")
                    emit(Resource.Success(message))
                }

                is DataResponse.DataError -> {
                    Log.d("CafeRepository", "등록 실패")
                    emit(Resource.Error(response.toString()))
                }
            }
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

    override suspend fun insertFavoriteCafe(cafe: Cafe): Boolean {
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

    override suspend fun updateRecentlyViewedCafe(cafe: Cafe): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun loadRecentlyViewedCafes(): Flow<Long> {
        return flow {
            recentlyViewedCafeDAO.selectAllRecentlyViewedCafes().collect { list ->
                list.forEach {
                    emit(it.cafeId)
                }
            }
        }
    }

    override suspend fun insertRecentlyViewedCafe(cafe: Cafe): Boolean {
        return try {
            recentlyViewedCafeDAO.insert(cafe.toRecentlyViewedCafeEntity())
            true
        } catch (e: IOException) {
            false
        }
    }
}