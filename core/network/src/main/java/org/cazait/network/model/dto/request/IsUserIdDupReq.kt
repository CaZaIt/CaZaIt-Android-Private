package org.cazait.network.model.dto.request

import com.google.gson.annotations.SerializedName

data class IsUserIdDupReq(
    @SerializedName("accountName")
    val userId: String
)
