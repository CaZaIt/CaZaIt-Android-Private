package org.cazait.database.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.cazait.core.model.congestion.CafeStatus
import java.util.Date

@Entity("FavoriteCafe")
data class FavoriteCafeEntity(
    @ColumnInfo
    val cafeId: String,
    @PrimaryKey(autoGenerate = true)
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
