package org.cazait.data.model.request

import com.google.gson.annotations.SerializedName

data class CafeMenuReq(
    @SerializedName("cafeId") val cafeId: Long
)
