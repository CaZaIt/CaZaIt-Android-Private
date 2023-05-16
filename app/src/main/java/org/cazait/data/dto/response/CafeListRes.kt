package org.cazait.data.dto.response

import com.google.gson.annotations.SerializedName
import org.cazait.data.model.CafeStatus
import org.cazait.domain.model.FavoriteCafe

data class ListCafesRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val cafes: List<List<CafeOfCafeList>>
)

data class ListFavoritesRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val favorites: List<FavoriteCafeRes>
)

data class FavoriteCafeRes(
    @SerializedName("favoritesId") val favoritesId: Long,
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("congestion") val congestionStatus: CafeStatus,
    @SerializedName("imageUrl") val imageUrl: List<String>
)