package org.cazait.core.data.datasource.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CafeMenuResponse(
    @Json(name = "code") val code: Int,
    @Json(name = "result") val result: String,
    @Json(name = "message") val message: String,
    @Json(name = "data") val menus: List<org.cazait.core.data.datasource.response.CafeMenuDTO>,
)

@JsonClass(generateAdapter = true)
data class CafeMenuDTO(
    @Json(name = "cafeMenuId")
    val menuId: Long,
    @Json(name = "name")
    val menuName: String,
    @Json(name = "description")
    val menuDesc: String,
    @Json(name = "price")
    val menuPrice: Int,
    @Json(name = "imageUrl")
    val image: String,
)
