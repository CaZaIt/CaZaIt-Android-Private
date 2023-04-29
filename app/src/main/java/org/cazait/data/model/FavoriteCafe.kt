package org.cazait.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteCafe(
    @SerializedName("favoritesId") val favoritesId: Long,
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("congestion") val congestion: String,
    @SerializedName("imageUrl") val imageUrl: List<String>
)
