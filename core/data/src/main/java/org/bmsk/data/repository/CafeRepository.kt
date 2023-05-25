package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.model.Cafe
import org.cazait.model.CafeMenus
import org.cazait.model.CafeReviews
import org.cazait.model.Cafes
import org.cazait.model.FavoriteCafe
import org.cazait.model.FavoriteCafes
import org.cazait.model.Resource

interface CafeRepository {
    suspend fun getListFavorites(userId: Long): Flow<Resource<FavoriteCafes>>
    suspend fun getListCafes(
        userId: Long?,
        latitude: String,
        longitude: String,
        sort: String = "distance",
        limit: String = "0"
    ): Flow<Resource<Cafes>>

    suspend fun getMenus(cafeId: Long): Flow<Resource<CafeMenus>>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Flow<Resource<CafeReviews>>

    suspend fun insertFavoriteCafe(cafe: FavoriteCafe): Boolean
    suspend fun insertFavoriteCafe(cafe: Cafe): Boolean

    suspend fun updateFavoriteCafe(cafe: FavoriteCafe): Boolean
    suspend fun loadFavoriteCafes(): Flow<Resource<FavoriteCafes>>
    suspend fun postFavoriteCafe(userId: Long, cafe: FavoriteCafe): Boolean
}