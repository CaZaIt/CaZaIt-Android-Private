package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName
import org.cazait.model.CafeImage
import org.cazait.model.CafeStatus

data class ListCafesRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val cafes: List<List<CafeOfCafeListDTO>>
)

data class CafeOfCafeListDTO(
    @SerializedName("cafeId")
    val cafeId: Long,
    @SerializedName("congestionStatus")
    val congestionStatus: CafeStatus,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("getCafeImageRes")
    val cafesImages: List<CafeImage>,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("favorite")
    val favorite: Boolean,
)

data class ListFavoritesRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val favorites: List<FavoriteCafeDTO>
)

data class FavoriteCafeDTO(
    @SerializedName("favoritesId")
    val favoritesId: Long,
    @SerializedName("cafeId")
    val cafeId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("congestion")
    val congestionStatus: CafeStatus,
    @SerializedName("imageUrl")
    val imageUrl: List<String>
)