package org.cazait.network.model.dto.request

import com.google.gson.annotations.SerializedName

data class SignUpReq(
    @SerializedName("accountName")
    val userId: String,
    val password: String,
    val phoneNumber: String,
    val nickname: String,
)
