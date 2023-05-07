package org.cazait.data.dto.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.cazait.data.model.CafeImage
import org.cazait.data.model.CafeStatus

@Parcelize
data class CafeOfCafeList(
    @SerializedName("cafeId") val cafeId: Long,
    @SerializedName("congestionStatus") val congestionStatus: CafeStatus,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("getCafeImageRes") val cafesImages: List<CafeImage>,
    @SerializedName("distance") val distance: Int,
    @SerializedName("favorite") val favorite: Boolean,
) : Parcelable