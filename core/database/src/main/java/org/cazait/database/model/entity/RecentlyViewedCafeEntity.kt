package org.cazait.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("RecentlyViewedCafe")
data class RecentlyViewedCafeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cafe_id")
    val cafeId: Long,
)