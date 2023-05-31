package org.cazait.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.cazait.database.model.entity.RecentlyViewedCafeEntity

@Dao
interface RecentlyViewedCafeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentlyViewedCafe: RecentlyViewedCafeEntity)

    @Query("SELECT * FROM RecentlyViewedCafe")
    fun getAllRecentlyViewedCafes(): Flow<List<RecentlyViewedCafeEntity>>

    @Delete
    fun delete(recentlyViewedCafe: RecentlyViewedCafeEntity)
}
