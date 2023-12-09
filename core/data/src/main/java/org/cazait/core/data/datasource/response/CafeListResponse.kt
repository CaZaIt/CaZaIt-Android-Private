package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.cazait.core.data.datasource.CafeDTO
import org.cazait.model.CafeStatus

@JsonClass(generateAdapter = true)
data class ListCafesResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val cafes: List<List<CafeDTO>>,
)

@JsonClass(generateAdapter = true)
data class ListFavoritesResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val favorites: List<org.cazait.core.data.datasource.response.FavoriteCafeDTO>,
)

@JsonClass(generateAdapter = true)
data class FavoriteCafeDTO(
    @Json(name = "favoritesId")
    val favoritesId: Long,
    @Json(name = "cafeId")
    val cafeId: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "address")
    val address: String,
    @Json(name = "latitude")
    val latitude: String,
    @Json(name = "longitude")
    val longitude: String,
    @Json(name = "congestion")
    val congestionStatus: CafeStatus,
    @Json(name = "imageUrl")
    val imageUrl: List<String>,
)
