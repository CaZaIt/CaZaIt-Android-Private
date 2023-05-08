package org.cazait.data.model

import com.google.gson.annotations.SerializedName

data class CafeMenu(
    @SerializedName("cafeMenuId") val menuId: Long,
    @SerializedName("name") val menuName: String,
    @SerializedName("description") val menuDesc: String,
    @SerializedName("price") val menuPrice: Int,
    @SerializedName("imageUrl") val image: String
) {
    fun getStringPrice() = menuPrice.toString()
}