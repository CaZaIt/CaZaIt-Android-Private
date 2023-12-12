package org.cazait.core.model.cafe

import org.cazait.core.model.congestion.CafeStatus
import java.io.Serializable

data class FavoriteCafes(
    var cafes: List<FavoriteCafe>,
) : Serializable

data class FavoriteCafe(
    val favoritesId: Long,
    val cafeId: String,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val congestionStatus: CafeStatus,
    val imageUrl: List<String>,
) : Serializable
