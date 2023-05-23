package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName

data class CafeMenuRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val menus: List<CafeMenuDTO>
)

data class CafeMenuDTO(
    @SerializedName("cafeMenuId")
    val menuId: Long,
    @SerializedName("name")
    val menuName: String,
    @SerializedName("description")
    val menuDesc: String,
    @SerializedName("price")
    val menuPrice: Int,
    @SerializedName("imageUrl")
    val image: String
)