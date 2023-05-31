package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName
import org.cazait.model.CafeStatus
import org.cazait.network.model.dto.CafeDTO

data class ListCafesRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val cafes: List<List<CafeDTO>>
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