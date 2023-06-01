package org.cazait.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.cazait.database.dao.CafeDAO
import org.cazait.database.dao.RecentlyViewedCafeDAO
import org.cazait.database.model.entity.FavoriteCafeEntity
import org.cazait.database.model.entity.RecentlyViewedCafeEntity

@Database(entities = [FavoriteCafeEntity::class, RecentlyViewedCafeEntity::class], version = 1)
@TypeConverters(DateConverter::class, ImageConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDAO(): CafeDAO

    abstract fun recentlyViewedCafeDAO(): RecentlyViewedCafeDAO
}