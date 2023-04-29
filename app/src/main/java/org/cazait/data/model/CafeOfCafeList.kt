package org.cazait.data.model

import com.google.gson.annotations.SerializedName

data class CafeOfCafeList(
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("congestionStatus") val congestionStatus: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("getCafeImageRes") val cafesImages: List<CafeImage>,
    @SerializedName("distance") val distance: Int,
    @SerializedName("favorite") val favorite: Boolean,
)
