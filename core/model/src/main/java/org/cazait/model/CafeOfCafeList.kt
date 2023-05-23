package org.cazait.model

data class CafeOfCafeList(
    val cafeId: Long,
    val congestionStatus: CafeStatus,
    val name: String,
    val address: String,
    val longitude: String,
    val latitude: String,
    val cafesImages: List<CafeImage>,
    val distance: Int,
    val favorite: Boolean,
)