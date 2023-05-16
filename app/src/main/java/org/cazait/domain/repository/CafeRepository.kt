package org.cazait.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.data.dto.response.CafeMenuRes
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.domain.model.Cafes
import org.cazait.domain.model.FavoriteCafe
import org.cazait.domain.model.FavoriteCafes
import org.cazait.domain.model.Resource

interface CafeRepository {
    suspend fun getListFavorites(userId: Long): Flow<Resource<FavoriteCafes>>
    suspend fun getListCafes(
        userId: Long?,
        latitude: String,
        longitude: String,
        sort: String = "distance",
        limit: String = "0"
    ): Flow<Resource<Cafes>>

    suspend fun getMenus(cafeId: Long): Flow<Resource<CafeMenuRes>>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Flow<Resource<CafeReviewRes>>

    suspend fun insertFavoriteCafe(cafe: FavoriteCafe): Boolean
    suspend fun updateFavoriteCafe(cafe: FavoriteCafe): Boolean
    suspend fun loadFavoriteCafes(): Flow<Resource<FavoriteCafes>>
    suspend fun postFavoriteCafe(userId: Long, cafe: FavoriteCafe): Boolean
}