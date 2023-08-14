package org.cazait.network.model.dto.request

import com.google.gson.annotations.SerializedName

data class VerificationCodeWithUserIdReq(
    @SerializedName("accountName")
    val userId: String,
    @SerializedName("recipientPhoneNumber")
    val phoneNumber: String
)
