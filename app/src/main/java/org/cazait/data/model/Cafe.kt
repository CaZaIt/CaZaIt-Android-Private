package org.cazait.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cafe(
    val cafeId: Long,
    val name: String,
    val address: String,
    val distance: Int,
    val status: String,
    val images: List<CafeImage>,
    val latitude: String? = null,
    val longitude: String? = null,
): Parcelable