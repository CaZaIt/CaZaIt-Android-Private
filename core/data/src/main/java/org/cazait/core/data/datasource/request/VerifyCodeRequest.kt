package org.cazait.core.data.datasource.request

data class VerifyCodeRequest(
    val recipientPhoneNumber: String,
    val verificationCode: Int,
)
