package org.cazait.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CafeImage(
    @SerializedName("cafeImageId") val cafeImageId: Long,
    @SerializedName("imageUrl") val imageUrl: String,
) : Parcelable
