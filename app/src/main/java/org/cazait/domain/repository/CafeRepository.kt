package org.cazait.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.data.Resource
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.dto.response.CafeMenuRes
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.data.dto.response.PostFavoriteCafeRes
import org.cazait.data.model.FavoriteCafe
import org.cazait.domain.model.Cafe

interface CafeRepository {
    suspend fun getListFavorites(userId: Long): Flow<Resource<ListFavoritesRes>>
    suspend fun getListCafes(
        userId: Long?,
        latitude: String,
        longitude: String,
        sort: String = "distance",
        limit: String = "0"
    ): Flow<Resource<ListCafesRes>>

    suspend fun getMenus(cafeId: Long): Flow<Resource<CafeMenuRes>>
    suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Flow<Resource<CafeReviewRes>>

    suspend fun insertFavoriteCafe(cafe: Cafe): Boolean
    suspend fun updateFavoriteCafe(cafe: Cafe): Boolean
    suspend fun loadFavoriteCafes(): Flow<List<Cafe>>
    suspend fun postFavoriteCafe(userId: Long, cafe: Cafe): Flow<Resource<PostFavoriteCafeRes>>
}