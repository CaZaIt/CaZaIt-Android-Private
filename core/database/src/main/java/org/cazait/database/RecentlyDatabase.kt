package org.cazait.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.cazait.database.dao.RecentlyViewedCafeDao
import org.cazait.database.model.entity.RecentlyViewedCafe


@Database(entities = arrayOf(RecentlyViewedCafe::class), version = 2, exportSchema = false)
abstract class RecentlyDatabase : RoomDatabase() {
    abstract fun recentlyViewedCafeDao(): RecentlyViewedCafeDao

    companion object {
        var INSTANCE: RecentlyDatabase? = null

        fun getInstance(context: Context): RecentlyDatabase? {
            if(INSTANCE == null){
                synchronized(RecentlyDatabase::class){
                    INSTANCE=Room.databaseBuilder(context.applicationContext,
                    RecentlyDatabase::class.java,"RecentlyDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
