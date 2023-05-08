package org.cazait.data.model.response

import com.google.gson.annotations.SerializedName
import org.cazait.data.model.CafeMenu
import org.cazait.data.model.CafeReview

data class CafeMenuRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val menus: List<CafeMenu>
)
