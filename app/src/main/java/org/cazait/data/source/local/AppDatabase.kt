package org.cazait.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.cazait.data.model.entity.FavoriteCafeEntity
import org.cazait.data.source.local.dao.CafeDAO

@Database(entities = [FavoriteCafeEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDAO(): CafeDAO
}