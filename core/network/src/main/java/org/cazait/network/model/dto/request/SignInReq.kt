package org.cazait.network.model.dto.request

import com.google.gson.annotations.SerializedName

data class SignInReq(
    @SerializedName("accountNumber")
    val userId: String,
    val password: String,
)
