package org.cazait.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.core.domain.model.cafe.CafeId
import org.cazait.core.domain.model.cafe.CafeName
import org.cazait.core.domain.model.cafe.Limit
import org.cazait.core.domain.model.cafe.Sort
import org.cazait.core.domain.model.location.Latitude
import org.cazait.core.domain.model.location.Longitude
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.model.CafeReviews
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.CafeMenus
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafes
import org.cazait.core.model.cafe.RecentlyViewedCafeInfo

interface CafeRepository {
    suspend fun getCafeById(cafeId: CafeId): NetworkResult<Cafe>
    suspend fun getListFavoritesAuth(userId: UserId): NetworkResult<FavoriteCafes>
    suspend fun getListCafes(
        latitude: Latitude,
        longitude: Longitude,
        sort: Sort = Sort.DISTANCE,
        limit: Limit = Limit(0),
    ): NetworkResult<Cafes>

    suspend fun getMenus(cafeId: CafeId): NetworkResult<CafeMenus>
    suspend fun getReviews(
        cafeId: CafeId,
        sortBy: String?,
        score: Int?,
        lastId: Long?,
    ): NetworkResult<CafeReviews>

    suspend fun postReviewAuth(
        userId: UserId,
        cafeId: CafeId,
        score: Int,
        content: String,
    ): NetworkResult<String>

    suspend fun postFavoriteCafeAuth(
        userId: UserId,
        cafeId: CafeId,
    ): NetworkResult<String>

    suspend fun deleteFavoriteCafeAuth(
        userId: UserId,
        cafeId: CafeId,
    ): NetworkResult<String>

    suspend fun insertRecentlyViewedCafe(
        recentlyViewedCafeInfo: RecentlyViewedCafeInfo,
    ): Boolean

    suspend fun loadRecentlyViewedCafes(): Flow<List<RecentlyViewedCafeInfo>>

    suspend fun getCafeSearch(
        cafeName: CafeName,
        latitude: Latitude,
        longitude: Longitude,
        sort: Sort = Sort.DISTANCE,
        limit: Limit = Limit(0),
    ): NetworkResult<Cafes>
}
