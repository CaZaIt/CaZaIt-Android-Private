package org.cazait.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.cazait.database.model.entity.FavoriteCafeEntity

@Dao
interface CafeDAO {
    @Query("SELECT * FROM FavoriteCafe ORDER BY createdDate DESC")
    fun selectAllFavoriteCafe(): Flow<List<FavoriteCafeEntity>>

    @Delete
    suspend fun deleteFavoriteCafe(item: FavoriteCafeEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteCafe(item: FavoriteCafeEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllFavoriteCafe(items: List<FavoriteCafeEntity>)
}