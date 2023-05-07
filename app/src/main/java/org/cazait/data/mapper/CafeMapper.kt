package org.cazait.data.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.cazait.R
import org.cazait.data.model.Cafe
import org.cazait.data.model.CafeStatus
import org.cazait.data.dto.response.CafeOfCafeList
import javax.inject.Inject

class CafeMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun itemCafeFromCafeOfCafeList(cafeOfCafeList: CafeOfCafeList): Cafe {
        val status = when (cafeOfCafeList.congestionStatus) {
            CafeStatus.FREE -> context.getString(R.string.state_free)
            CafeStatus.NORMAL -> context.getString(R.string.state_normal)
            CafeStatus.CLOSE -> context.getString(R.string.state_close)
            CafeStatus.CROWDED -> context.getString(R.string.state_crowded)
            CafeStatus.VERY_CROWDED -> context.getString(R.string.state_very_crowded)
            CafeStatus.NONE -> context.getString(R.string.state_normal)
        }
        return Cafe(
            cafeId = cafeOfCafeList.cafeId,
            name = cafeOfCafeList.name,
            address = cafeOfCafeList.address,
            distance = cafeOfCafeList.distance,
            status = status,
            images = cafeOfCafeList.cafesImages
        )
    }
}