package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.cazait.core.data.datasource.CafeDTO
import org.cazait.model.Cafe
import org.cazait.model.CafeStatus

@JsonClass(generateAdapter = true)
data class CafeResponse(
    @Json(name = "code")
    val code: Int,
    @Json(name = "result")
    val result: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val cafe: CafeDTO,
)

@JsonClass(generateAdapter = true)
data class CafeResTemp(
    @Json(name = "code") val code: Int,
    @Json(name = "result") val result: String,
    @Json(name = "message") val message: String,
    @Json(name = "data") val cafe: org.cazait.core.data.datasource.response.CafeResTemp.Data,
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "cafeId")
        val cafeId: String,
        @Json(name = "congestionStatus")
        val congestionStatus: CafeStatus,
        @Json(name = "name")
        val name: String,
        @Json(name = "address")
        val address: String,
        @Json(name = "longitude")
        val longitude: String,
        @Json(name = "latitude")
        val latitude: String,
        @Json(name = "cafeImageRes")
        val cafesImages: List<org.cazait.core.data.datasource.response.CafeResTemp.CafeImage>,
        @Json(name = "distance")
        val distance: Int,
        @Json(name = "favorite")
        val favorite: Boolean,
        @Json(name = "timestamp")
        val timestamp: Long,
    )

    @JsonClass(generateAdapter = true)
    data class CafeImage(
        @Json(name = "cafeImageId")
        val cafeImageId: Long,
        @Json(name = "imageUrl")
        val imageUrl: String,
    )
}

fun org.cazait.core.data.datasource.response.CafeResTemp.Data.toCafe(): Cafe {
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
        timestamp = timestamp,
    )
}
