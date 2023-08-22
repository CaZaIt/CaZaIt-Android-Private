package org.cazait.network.model.dto.request

data class FindUserIdReq(
    val userPhoneNumber: String
)

data class CheckUserDataReq(
    val phoneNumber: String
)