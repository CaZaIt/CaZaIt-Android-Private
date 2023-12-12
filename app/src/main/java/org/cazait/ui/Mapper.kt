package org.cazait.ui

import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.FavoriteCafe

object Mapper {
    fun FavoriteCafe.toCafe() = Cafe(
        cafeId = cafeId,
        name = name,
        address = address,
        distance = 0,
        status = congestionStatus,
        images = imageUrl,
        latitude = latitude,
        longitude = longitude,
        timestamp = System.currentTimeMillis(),
    )
}
