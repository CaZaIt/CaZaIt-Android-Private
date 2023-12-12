package org.cazait.core.data.datasource

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.cazait.core.model.congestion.CafeStatus

@JsonClass(generateAdapter = true)
data class CafeDTO(
    @Json(name = "cafeId")
    val cafeId: String,
    @Json(name = "congestionStatus")
    val congestionStatus: CafeStatus,
    @Json(name = "name")
    val name: String,
    @Json(name = "address")
    val address: String,
    @Json(name = "longitude")
    val longitude: String,
    @Json(name = "latitude")
    val latitude: String,
    @Json(name = "cafeImages")
    val cafesImages: List<String>,
    @Json(name = "distance")
    val distance: Int,
    @Json(name = "favoriteStatus")
    val favorite: String,
)
