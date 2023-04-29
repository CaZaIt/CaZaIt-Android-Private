package org.cazait.data.model

import com.google.gson.annotations.SerializedName

data class CafeImage(
    @SerializedName("cafeImageId") val cafeImageId: Long,
    @SerializedName("imageUrl") val imageUrl: String,
)
