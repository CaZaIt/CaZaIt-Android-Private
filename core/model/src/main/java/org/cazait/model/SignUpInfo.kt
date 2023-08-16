package org.cazait.model

data class SignUpInfo(
    val uuid: String,
    val userId: String,
    val phoneNumber: String,
    val nickname: String,
)

data class VerificationCode(
    val verify: Boolean = false,
    val message: String
)

data class VerifyCode(
    val verify: Boolean = false,
    val message: String
)