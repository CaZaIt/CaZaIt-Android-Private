package org.cazait.network.model.dto.response

import com.google.gson.annotations.SerializedName
import org.cazait.network.model.dto.CafeDTO

data class CafeRes(
    @SerializedName("code") val code: Int,
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val cafe: CafeDTO
)
