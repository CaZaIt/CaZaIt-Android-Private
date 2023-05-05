package org.cazait.data.dto.response

import com.google.gson.annotations.SerializedName
import org.cazait.data.model.FavoriteCafe
import org.cazait.data.dto.response.CafeOfCafeList

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
    @SerializedName("data") val favorites: List<FavoriteCafe>
)