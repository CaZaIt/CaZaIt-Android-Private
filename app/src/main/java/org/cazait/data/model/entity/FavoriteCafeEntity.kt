package org.cazait.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.cazait.data.model.CafeStatus
import java.util.Date

@Entity("FavoriteCafe")
data class FavoriteCafeEntity(
    @PrimaryKey(false)
    val cafeId: Long,
    @ColumnInfo
    var cafeName: String,
    @ColumnInfo
    val address: String,
    @ColumnInfo
    val latitude: String,
    @ColumnInfo
    val longitude: String,
    @ColumnInfo
    val congestion: CafeStatus,
    @ColumnInfo
    val imageUrl: String,
    @ColumnInfo
    val createdDate: Date
)
