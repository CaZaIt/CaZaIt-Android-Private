package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.CafeDTO
import org.cazait.core.data.datasource.response.CafeResponse
import org.cazait.core.data.datasource.response.FavoriteCafeDTO
import org.cazait.core.data.datasource.response.ListCafesResponse
import org.cazait.core.data.datasource.response.ListFavoritesResponse
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafe
import org.cazait.core.model.cafe.FavoriteCafes
import java.util.Date

internal fun CafeResponse.toData(): Cafe = Cafe(
    cafeId = this.cafe.cafeId,
    name = this.cafe.name,
    address = this.cafe.address,
    distance = this.cafe.distance,
    status = this.cafe.congestionStatus,
    images = this.cafe.cafesImages,
    latitude = this.cafe.latitude,
    longitude = this.cafe.longitude,
    timestamp = Date().time,
)

internal fun ListCafesResponse.toData(): Cafes =
    Cafes(cafes = this.cafes.flatMap { it.map(CafeDTO::toData) })

internal fun CafeDTO.toData(): Cafe = Cafe(
    cafeId = this.cafeId,
    name = this.name,
    address = this.address,
    distance = this.distance,
    status = this.congestionStatus,
    images = this.cafesImages,
    latitude = this.latitude,
    longitude = this.longitude,
    timestamp = Date().time,
)

internal fun ListFavoritesResponse.toData(): FavoriteCafes = FavoriteCafes(
    cafes = this.favorites.map(FavoriteCafeDTO::toData),
)

internal fun FavoriteCafeDTO.toData(): FavoriteCafe = FavoriteCafe(
    favoritesId = this.favoritesId,
    cafeId = this.cafeId,
    name = this.name,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude,
    congestionStatus = this.congestionStatus,
    imageUrl = this.imageUrl,
)
