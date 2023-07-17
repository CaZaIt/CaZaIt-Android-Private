package org.cazait.network.model.dto.request

data class SignUpReq(
    val accountNumber: String,
    val password: String,
    val phoneNumber: String,
    val nickname: String,
)
