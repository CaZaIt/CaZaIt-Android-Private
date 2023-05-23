package org.cazait.model

import java.io.Serializable

data class Cafes(
    val list: List<Cafe>
)
data class Cafe(
    val cafeId: Long,
    val name: String,
    val address: String,
    val distance: Int,
    val status: CafeStatus = CafeStatus.NONE,
    val images: List<CafeImage>,
    val latitude: String? = null,
    val longitude: String? = null,
): Serializable