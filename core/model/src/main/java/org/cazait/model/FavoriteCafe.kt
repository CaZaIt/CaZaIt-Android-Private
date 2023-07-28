package org.cazait.model

import java.io.Serializable

data class FavoriteCafes(
    var list: List<FavoriteCafe>
) : Serializable

data class FavoriteCafe(
    val favoritesId: Long,
    val cafeId: Long,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val congestionStatus: CafeStatus,
    val imageUrl: List<String>
) : Serializable
