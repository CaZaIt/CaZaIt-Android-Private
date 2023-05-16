package org.cazait.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.cazait.data.model.CafeImage
import org.cazait.data.model.CafeStatus

data class Cafes(
    val list: List<Cafe>
)

@Parcelize
data class Cafe(
    val cafeId: Long,
    val name: String,
    val address: String,
    val distance: Int,
    val status: CafeStatus = CafeStatus.NONE,
    val images: List<CafeImage>,
    val latitude: String? = null,
    val longitude: String? = null,
): Parcelable