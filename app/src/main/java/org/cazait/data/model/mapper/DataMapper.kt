package org.cazait.data.model.mapper

import org.cazait.data.dto.response.CafeOfCafeList
import org.cazait.data.dto.response.FavoriteCafeRes
import org.cazait.data.model.entity.FavoriteCafeEntity
import org.cazait.domain.model.Cafe
import org.cazait.domain.model.FavoriteCafe
import java.util.Date

object DataMapper {
    fun CafeOfCafeList.toCafe() = Cafe(
        cafeId = cafeId,
        name = name,
        address = address,
        distance = distance,
        status = congestionStatus,
        images = cafesImages,
        latitude = latitude,
        longitude = longitude,
    )

    fun FavoriteCafeRes.toFavoriteCafe() = FavoriteCafe(
        favoritesId = favoritesId,
        cafeId = cafeId,
        name = name,
        address = address,
        latitude = latitude,
        longitude = longitude,
        congestionStatus = congestionStatus,
        imageUrl = imageUrl
    )

    fun FavoriteCafe.toFavoriteCafeEntity() = FavoriteCafeEntity(
        favoritesId = favoritesId,
        cafeId = cafeId,
        name = name,
        address = address,
        latitude = latitude,
        longitude = longitude,
        congestionStatus = congestionStatus,
        imageUrl = imageUrl,
        createdDate = Date()
    )

    fun FavoriteCafeEntity.toFavoriteCafe() = FavoriteCafe(
        favoritesId = favoritesId,
        cafeId = cafeId,
        name = name,
        address = address,
        latitude = latitude,
        longitude = longitude,
        congestionStatus = congestionStatus,
        imageUrl = imageUrl,
    )
}
