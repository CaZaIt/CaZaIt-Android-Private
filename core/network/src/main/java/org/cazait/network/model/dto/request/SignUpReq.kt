package org.cazait.network.model.dto.request

data class SignUpReq(
    val email: String,
    val password: String,
    val nickname: String,
)
