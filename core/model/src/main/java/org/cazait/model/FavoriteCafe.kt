package org.cazait.model

data class FavoriteCafes(
    val list: List<FavoriteCafe>
)

data class FavoriteCafe(
    val favoritesId: Long,
    val cafeId: Long,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val congestionStatus: CafeStatus,
    val imageUrl: List<String>
)
