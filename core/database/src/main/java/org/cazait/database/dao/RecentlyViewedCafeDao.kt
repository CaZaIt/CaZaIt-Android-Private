package org.cazait.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.cazait.database.model.entity.RecentlyViewedCafe

@Dao
interface RecentlyViewedCafeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentlyViewedCafe: RecentlyViewedCafe)

    @Query("SELECT * FROM recently_viewed_cafes")
    fun getAllRecentlyViewedCafes(): LiveData<List<RecentlyViewedCafe>>

    @Delete
    fun delete(recentlyViewedCafe: RecentlyViewedCafe)

}
