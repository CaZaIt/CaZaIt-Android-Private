package org.cazait.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteCafe(
    @SerializedName("favoritesId") val favoritesId: Long,
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("congestion") val congestionStatus: CafeStatus,
    @SerializedName("imageUrl") val imageUrl: List<String>
): Parcelable
