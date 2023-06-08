package org.cazait.ui.component

import org.cazait.model.Cafe
import org.cazait.model.CafeImage
import org.cazait.model.FavoriteCafe

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
    )
}