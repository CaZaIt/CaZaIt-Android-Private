package org.cazait.model

data class UserAccount(
    val userId: String
)

data class UserPassword(
    val uuid: String,
    val userId: String,
    val password: String,
    val phoneNumber: String,
    val nickname: String,
)