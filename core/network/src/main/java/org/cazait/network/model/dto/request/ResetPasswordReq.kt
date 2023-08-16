package org.cazait.network.model.dto.request

data class ResetPasswordReq(
    val userPhoneNumber: String,
    val password: String
)
