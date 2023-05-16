package org.cazait.domain.model.mapper

import org.cazait.data.model.CafeImage
import org.cazait.domain.model.Cafe
import org.cazait.domain.model.FavoriteCafe

object DomainMapper {
    fun FavoriteCafe.toCafe() = Cafe(
        cafeId = cafeId,
        name = name,
        address = address,
        distance = 0,
        status = congestionStatus,
        images = imageUrl.map { CafeImage(0L, it) },
        latitude = latitude,
        longitude = longitude
    )

    fun Cafe.toFavoriteCafe() = FavoriteCafe(
        favoritesId = cafeId,
        cafeId = cafeId,
        name = name,
        address = address,
        latitude = latitude ?: "0",
        longitude = longitude ?: "0",
        congestionStatus = status,
        imageUrl = images.map { it.imageUrl }
    )
}