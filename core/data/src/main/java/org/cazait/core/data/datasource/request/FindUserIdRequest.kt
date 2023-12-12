package org.cazait.core.data.datasource.request

data class FindUserIdRequest(
    val userPhoneNumber: String,
)

data class CheckUserDataRequest(
    val phoneNumber: String,
)
