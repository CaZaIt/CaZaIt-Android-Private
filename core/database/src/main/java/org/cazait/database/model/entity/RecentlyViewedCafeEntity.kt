package org.cazait.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("RecentlyViewedCafe")
data class RecentlyViewedCafeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cafe_id")
    val cafeId: Long,
    @ColumnInfo
    val timestamp: Long,
)
