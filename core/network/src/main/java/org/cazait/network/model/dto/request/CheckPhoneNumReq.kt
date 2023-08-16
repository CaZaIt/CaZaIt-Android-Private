package org.cazait.network.model.dto.request

data class CheckPhoneNumReq(
    val phoneNumber: String,
    val isExist: String
)
