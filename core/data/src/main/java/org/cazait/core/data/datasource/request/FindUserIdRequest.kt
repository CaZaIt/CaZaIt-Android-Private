package org.cazait.core.data.datasource.request

import com.google.gson.annotations.SerializedName

data class FindUserIdRequest(
    @SerializedName("userPhoneNumber")
    val userPhoneNumber: String,
)

data class CheckUserDataRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
)
