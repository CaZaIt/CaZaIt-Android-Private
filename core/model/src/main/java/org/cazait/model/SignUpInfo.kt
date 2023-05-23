package org.cazait.model

data class SignUpInfo(
    val id: Long,
    val email: String,
    val password: String,
    val nickname: String,
)

data class EmailDup(
    val isDup: Boolean = false,
    val message: String,
)

data class NicknameDup(
    val isDup: Boolean = false,
    val message: String,
)