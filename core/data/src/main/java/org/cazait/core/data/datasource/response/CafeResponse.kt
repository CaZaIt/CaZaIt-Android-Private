package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.cazait.core.data.datasource.CafeDTO
import org.cazait.core.model.congestion.CafeStatus

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
    @Json(name = "data") val cafe: Data,
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
        val cafesImages: List<CafeImage>,
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
