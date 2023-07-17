package org.cazait.network.model.dto.request

data class VerifyCodeReq(
    val recipientPhoneNumber: String,
    val verificationCode: Int
)
