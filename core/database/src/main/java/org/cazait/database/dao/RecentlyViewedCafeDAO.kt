package org.cazait.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.cazait.database.model.entity.RecentlyViewedCafeEntity

@Dao
interface RecentlyViewedCafeDAO {

    @Query("SELECT * FROM RecentlyViewedCafe")
    fun selectAllRecentlyViewedCafes(): Flow<List<RecentlyViewedCafeEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(recentlyViewedCafe: RecentlyViewedCafeEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(recentlyViewedCafes: List<RecentlyViewedCafeEntity>)

    @Delete
    suspend fun delete(recentlyViewedCafe: RecentlyViewedCafeEntity)
}
