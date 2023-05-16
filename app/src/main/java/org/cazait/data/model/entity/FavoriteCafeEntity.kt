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
    val favoritesId: Long,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    val address: String,
    @ColumnInfo
    val latitude: String,
    @ColumnInfo
    val longitude: String,
    @ColumnInfo
    val congestionStatus: CafeStatus,
    @ColumnInfo
    val imageUrl: List<String>,
    @ColumnInfo
    val createdDate: Date
)
