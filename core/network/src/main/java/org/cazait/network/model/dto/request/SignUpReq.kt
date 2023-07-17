package org.cazait.network.model.dto.request

data class SignUpReq(
    val idNumber: String,
    val password: String,
    val phoneNumber: String,
    val nickname: String,
)
