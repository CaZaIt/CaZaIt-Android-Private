package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.model.Cafe
import org.cazait.model.CafeMenus
import org.cazait.model.CafeReviews
import org.cazait.model.Cafes
import org.cazait.model.FavoriteCafe
import org.cazait.model.FavoriteCafes
import org.cazait.model.RecentlyViewedCafe
import org.cazait.model.Resource

interface CafeRepository {
    suspend fun getCafeById(cafeId: String): Flow<Resource<Cafe>>
    suspend fun getListFavoritesAuth(userId: String): Flow<Resource<FavoriteCafes>>
    suspend fun getListCafes(
        latitude: String,
        longitude: String,
        sort: String = "distance",
        limit: String = "0"
    ): Flow<Resource<Cafes>>

    suspend fun getMenus(cafeId: String): Flow<Resource<CafeMenus>>
    suspend fun getReviews(
        cafeId: String,
        sortBy: String?,
        nums: Int,
        score: Int?
    ): Flow<Resource<CafeReviews>>

    suspend fun postReviewAuth(
        userId: String,
        cafeId: String,
        score: Int,
        content: String
    ): Flow<Resource<String>>

    suspend fun patchReviewAuth(
        reviewId: String,
        score: Int,
        content: String
    ): Flow<Resource<String>>

    suspend fun deleteReviewAuth(reviewId: String): Flow<Resource<String>>

    suspend fun insertFavoriteCafe(cafe: FavoriteCafe): Boolean
    suspend fun insertFavoriteCafe(cafe: Cafe): Boolean

    suspend fun updateFavoriteCafe(cafe: FavoriteCafe): Boolean
    suspend fun loadFavoriteCafes(): Flow<Resource<FavoriteCafes>>
    suspend fun postFavoriteCafeAuth(userId: String, cafe: FavoriteCafe): Flow<Resource<String>>
    suspend fun postFavoriteCafeAuth(userId: String, cafe: Cafe): Flow<Resource<String>>
    suspend fun deleteFavoriteCafeAuth(userId: String, cafe: Cafe): Flow<Resource<String>>
    suspend fun deleteFavoriteCafeAuth(userId: String, cafe: FavoriteCafe): Flow<Resource<String>>
    suspend fun localDeleteFavoriteCafe(cafe: Cafe): Boolean
    suspend fun localDeleteFavoriteCafe(cafe: FavoriteCafe): Boolean
    suspend fun insertRecentlyViewedCafe(cafe: Cafe): Boolean

    suspend fun updateRecentlyViewedCafe(cafe: Cafe): Boolean
    suspend fun loadRecentlyViewedCafes(): Flow<RecentlyViewedCafe>

    suspend fun getCafeSearch(
        cafeName: String,
        longitude: String,
        latitude: String,
        sort: String = "distance",
        limit: String = "0"
    ): Flow<Resource<Cafes>>
}