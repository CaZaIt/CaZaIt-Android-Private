package org.cazait.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.cazait.core.data.datasource.CafeInfoRemoteDataSource
import org.cazait.core.data.datasource.CafeListRemoteDataSource
import org.cazait.core.data.datasource.request.CafeReviewPostRequest
import org.cazait.core.data.datasource.response.CafeMenuResponse
import org.cazait.core.data.datasource.response.CafeResponse
import org.cazait.core.data.datasource.response.CafeReviewPostResponse
import org.cazait.core.data.datasource.response.CafeReviewResponse
import org.cazait.core.data.datasource.response.ListCafesResponse
import org.cazait.core.data.datasource.response.ListFavoritesResponse
import org.cazait.core.data.mapper.toData
import org.cazait.core.data.mapper.toEntity
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.repository.CafeRepository
import org.cazait.core.model.CafeReviews
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.CafeMenus
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafes
import org.cazait.core.model.cafe.RecentlyViewedCafeInfo
import org.cazait.database.dao.RecentlyViewedCafeDAO
import org.cazait.database.model.entity.RecentlyViewedCafeEntity
import java.io.IOException
import javax.inject.Inject

class CafeRepositoryImpl @Inject constructor(
    private val cafeInfoRemoteDataSource: CafeInfoRemoteDataSource,
    private val cafeListRemoteDataSource: CafeListRemoteDataSource,
    private val recentlyViewedCafeDAO: RecentlyViewedCafeDAO,
) : CafeRepository {

    override suspend fun getCafeById(cafeId: String): NetworkResult<Cafe> {
        return cafeInfoRemoteDataSource.getCafe(cafeId = cafeId).map(CafeResponse::toData)
    }

    override suspend fun getListCafes(
        latitude: String,
        longitude: String,
        sort: String,
        limit: String,
    ): NetworkResult<Cafes> {
        return cafeListRemoteDataSource.getListCafes(
            latitude = latitude,
            longitude = longitude,
            sort = sort,
            limit = limit,
        ).map(ListCafesResponse::toData)
    }

    override suspend fun getListFavoritesAuth(userId: String): NetworkResult<FavoriteCafes> {
        return cafeListRemoteDataSource.getListFavoritesAuth(userId = userId)
            .map(ListFavoritesResponse::toData)
    }

    override suspend fun getMenus(cafeId: String): NetworkResult<CafeMenus> {
        return cafeInfoRemoteDataSource.getMenus(cafeId = cafeId).map(CafeMenuResponse::toData)
    }

    override suspend fun getReviews(
        cafeId: String,
        sortBy: String?,
        score: Int?,
        lastId: Long?,
    ): NetworkResult<CafeReviews> {
        return cafeInfoRemoteDataSource.getReviews(
            cafeId = cafeId,
            sortBy = sortBy,
            score = score,
            lastId = lastId,
        ).map(CafeReviewResponse::toData)
    }

    override suspend fun postReviewAuth(
        userId: String,
        cafeId: String,
        score: Int,
        content: String,
    ): NetworkResult<String> {
        val cafeReviewPostRequest = CafeReviewPostRequest(score, content)
        return cafeInfoRemoteDataSource.postReview(
            userId = userId,
            cafeId = cafeId,
            cafeReviewPostRequest = cafeReviewPostRequest,
        ).map(CafeReviewPostResponse::toData)
    }

    override suspend fun postFavoriteCafeAuth(
        userId: String,
        cafeId: String,
    ): NetworkResult<String> {
        return cafeInfoRemoteDataSource.postFavoriteCafe(
            userId = userId,
            cafeId = cafeId,
        ).map { it.message }
    }

    override suspend fun deleteFavoriteCafeAuth(
        userId: String,
        cafeId: String,
    ): NetworkResult<String> {
        return cafeInfoRemoteDataSource.deleteFavoriteCafe(
            userId = userId,
            cafeId = cafeId,
        ).map { it.message }
    }

    override suspend fun loadRecentlyViewedCafes(): Flow<List<RecentlyViewedCafeInfo>> {
        return recentlyViewedCafeDAO.selectAllRecentlyViewedCafes()
            .map { it.map(RecentlyViewedCafeEntity::toData) }
    }

    override suspend fun insertRecentlyViewedCafe(
        recentlyViewedCafeInfo: RecentlyViewedCafeInfo,
    ): Boolean {
        return try {
            recentlyViewedCafeDAO.insert(recentlyViewedCafeInfo.toEntity())
            true
        } catch (e: IOException) {
            false
        }
    }

    override suspend fun getCafeSearch(
        cafeName: String,
        longitude: String,
        latitude: String,
        sort: String,
        limit: String,
    ): NetworkResult<Cafes> {
        return cafeListRemoteDataSource.getCafeSearch(
            cafeName = cafeName,
            longitude = longitude,
            latitude = latitude,
            sort = sort,
            limit = limit,
        ).map(ListCafesResponse::toData)
    }
}
