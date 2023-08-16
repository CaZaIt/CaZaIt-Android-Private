package org.cazait.network.model.dto

import com.google.gson.annotations.SerializedName
import org.cazait.model.CafeStatus

data class CafeDTO(
    @SerializedName("cafeId")
    val cafeId: String,
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
    @SerializedName("cafeImages")
    val cafesImages: List<String>,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("favorite")
    val favorite: String,
)