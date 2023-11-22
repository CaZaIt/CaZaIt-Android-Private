package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName
import org.cazait.model.Cafe
import org.cazait.model.CafeStatus
import org.cazait.network.model.dto.CafeDTO

data class CafeRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val cafe: CafeDTO
)

data class CafeResTemp(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val cafe: Data
) {
    data class Data(
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
        @SerializedName("cafeImageRes")
        val cafesImages: List<CafeImage>,
        @SerializedName("distance")
        val distance: Int,
        @SerializedName("favorite")
        val favorite: Boolean,
        @SerializedName("timestamp")
        val timestamp: Long,
    )

    data class CafeImage(
        val cafeImageId: Long,
        val imageUrl: String,
    )


}

fun CafeResTemp.Data.toCafe(): Cafe {
    val cafesImages = cafesImages
    return Cafe(
        cafeId = cafeId,
        name = name,
        address = address,
        distance = distance,
        status = congestionStatus,
        images = cafesImages.map { it.imageUrl },
        latitude = latitude,
        longitude = longitude,
        timestamp = timestamp
    )
}
