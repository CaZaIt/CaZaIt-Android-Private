package org.cazait.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.model.CafeReviews
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.CafeMenus
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafes
import org.cazait.core.model.cafe.RecentlyViewedCafeInfo

interface CafeRepository {
    suspend fun getCafeById(cafeId: String): NetworkResult<Cafe>
    suspend fun getListFavoritesAuth(userId: String): NetworkResult<FavoriteCafes>
    suspend fun getListCafes(
        latitude: String,
        longitude: String,
        sort: String = "distance",
        limit: String = "0",
    ): NetworkResult<Cafes>

    suspend fun getMenus(cafeId: String): NetworkResult<CafeMenus>
    suspend fun getReviews(
        cafeId: String,
        sortBy: String?,
        score: Int?,
        lastId: Long?,
    ): NetworkResult<CafeReviews>

    suspend fun postReviewAuth(
        userId: String,
        cafeId: String,
        score: Int,
        content: String,
    ): NetworkResult<String>

    suspend fun postFavoriteCafeAuth(userId: String, cafeId: String): NetworkResult<String>
    suspend fun deleteFavoriteCafeAuth(userId: String, cafeId: String): NetworkResult<String>
    suspend fun insertRecentlyViewedCafe(recentlyViewedCafeInfo: RecentlyViewedCafeInfo): Boolean

    suspend fun loadRecentlyViewedCafes(): Flow<List<RecentlyViewedCafeInfo>>

    suspend fun getCafeSearch(
        cafeName: String,
        latitude: String,
        longitude: String,
        sort: String = "distance",
        limit: String = "0",
    ): NetworkResult<Cafes>
}
