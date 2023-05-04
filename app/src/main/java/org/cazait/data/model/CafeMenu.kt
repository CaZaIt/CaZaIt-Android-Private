package org.cazait.data.model

import com.google.gson.annotations.SerializedName

data class CafeMenu(
    @SerializedName("cafeMenuId") val menuId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val desc: String,
    @SerializedName("price") val price: Int,
    @SerializedName("imageUrl") val imageUrl: String
) {
    fun getStringPrice() = price.toString()
}