package org.cazait.model

data class SignUpInfo(
    val uuid: String,
    val userId: String,
    val phoneNumber: String,
    val nickname: String,
)

data class IdDup(
    val isDup: Boolean = false,
    val message: String,
)

data class NicknameDup(
    val isDup: Boolean = false,
    val message: String,
)

data class SignUpCode(
    val verify: Boolean = false,
    val message: String
)

data class VerifyCode(
    val verify: Boolean = false,
    val message: String
)