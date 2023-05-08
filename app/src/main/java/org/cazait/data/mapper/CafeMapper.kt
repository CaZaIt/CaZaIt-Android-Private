package org.cazait.data.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.cazait.R
import org.cazait.data.model.Cafe
import org.cazait.data.model.CafeStatus
import org.cazait.data.dto.response.CafeOfCafeList
import org.cazait.data.model.CafeImage
import org.cazait.data.model.FavoriteCafe
import javax.inject.Inject

class CafeMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun itemCafeFromFavoriteCafe(cafeOfFavoriteCafe: FavoriteCafe): Cafe {
        return createCafe(
            cafeId = cafeOfFavoriteCafe.cafeId,
            name = cafeOfFavoriteCafe.name,
            address = cafeOfFavoriteCafe.address,
            distance = 0,
            congestionStatus = cafeOfFavoriteCafe.congestionStatus,
            images = cafeOfFavoriteCafe.imageUrl.map { cafeImageFromImageUrl(it) }
        )
    }

    fun cafeImageFromImageUrl(url: String): CafeImage {
        return CafeImage(
            cafeImageId = 0L,
            imageUrl = url
        )
    }

    fun itemCafeFromCafeOfCafeListWithLatLng(cafeOfCafeList: CafeOfCafeList): Cafe {
        return createCafe(
            cafeId = cafeOfCafeList.cafeId,
            name = cafeOfCafeList.name,
            address = cafeOfCafeList.address,
            distance = cafeOfCafeList.distance,
            congestionStatus = cafeOfCafeList.congestionStatus,
            images = cafeOfCafeList.cafesImages,
            latitude = cafeOfCafeList.latitude,
            longitude = cafeOfCafeList.longitude,
        )
    }

    private fun getCafeStatusString(cafeStatus: CafeStatus): String {
        return when (cafeStatus) {
            CafeStatus.FREE -> context.getString(R.string.state_free)
            CafeStatus.NORMAL -> context.getString(R.string.state_normal)
            CafeStatus.CLOSE -> context.getString(R.string.state_close)
            CafeStatus.CROWDED -> context.getString(R.string.state_crowded)
            CafeStatus.VERY_CROWDED -> context.getString(R.string.state_very_crowded)
            CafeStatus.NONE -> context.getString(R.string.state_normal)
        }
    }

    private fun createCafe(
        cafeId: Long,
        name: String,
        address: String,
        distance: Int,
        congestionStatus: CafeStatus,
        images: List<CafeImage>,
        latitude: String? = null,
        longitude: String? = null,
    ): Cafe {
        val status = getCafeStatusString(congestionStatus)
        return Cafe(cafeId, name, address, distance, status, images, latitude, longitude)
    }

    fun itemCafeFromCafeOfCafeList(cafeOfCafeList: CafeOfCafeList): Cafe {
        return createCafe(
            cafeId = cafeOfCafeList.cafeId,
            name = cafeOfCafeList.name,
            address = cafeOfCafeList.address,
            distance = cafeOfCafeList.distance,
            congestionStatus = cafeOfCafeList.congestionStatus,
            images = cafeOfCafeList.cafesImages
        )
    }
}