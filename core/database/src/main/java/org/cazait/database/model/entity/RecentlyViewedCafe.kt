package org.cazait.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.cazait.model.CafeStatus


@Entity("recently_viewed_cafes")
data class RecentlyViewedCafe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var cafeId: Long?,
    @ColumnInfo
    var name: String = "",
    @ColumnInfo
    val address: String = "",
    @ColumnInfo
    val distance: Int? = null,
    @ColumnInfo
    val latitude: String? = null,
    @ColumnInfo
    val status: CafeStatus = CafeStatus.NONE
)