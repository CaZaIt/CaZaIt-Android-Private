package org.cazait.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.cazait.data.model.CafeStatus

data class FavoriteCafes(
    val list: List<FavoriteCafe>
)

@Parcelize
data class FavoriteCafe(
    val favoritesId: Long,
    val cafeId: Long,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val congestionStatus: CafeStatus,
    val imageUrl: List<String>
): Parcelable
