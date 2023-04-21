package org.cazait.data.model.response

import com.google.gson.annotations.SerializedName

data class ListCafesRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val cafes: CafesRes
)

data class CafesRes(
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("congestionStatus") val congestionStatus: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("getCafeImageRes") val cafesImages: CafeImageRes,
    @SerializedName("distance") val distance: Int,
    @SerializedName("favorite") val favorite: Boolean,
)

data class CafeImageRes(
    @SerializedName("cafeImageId") val cafeImageId: Long,
    @SerializedName("imageUrl") val imageUrl: String,
)

data class ListFavoritesRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val favorites: FavoritesRes
)

data class FavoritesRes(
    @SerializedName("favoritesId") val favoritesId: Long,
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("congestion") val congestion: String,
    @SerializedName("imageUrl") val imageUrl: List<String>
)