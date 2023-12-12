package org.cazait.core.model.cafe

import org.cazait.core.model.congestion.CafeStatus
import java.io.Serializable

data class Cafes(
    val cafes: List<Cafe>,
)

data class Cafe(
    val cafeId: String,
    val name: String,
    val address: String,
    val distance: Int,
    val status: CafeStatus = CafeStatus.NONE,
    val images: List<String>,
    val latitude: String? = null,
    val longitude: String? = null,
    val timestamp: Long,
    var isFavorite: Boolean = false,
) : Serializable
