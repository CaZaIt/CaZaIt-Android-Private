package org.cazait.model

data class SignUpInfo(
    val id: String,
    val idNumber: String,
    val password: String,
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

data class Message(
    val verify: Boolean = false,
    val message: String
)