package org.cazait.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("RecentlyViewedCafe")
data class RecentlyViewedCafeEntity(
    @ColumnInfo(name = "cafe_id")
    val cafeId: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val timestamp: Long,
)
