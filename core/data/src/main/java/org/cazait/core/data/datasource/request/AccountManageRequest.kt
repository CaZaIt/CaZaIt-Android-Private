package org.cazait.core.data.datasource.request

data class CheckPasswordRequest(
    val password: String,
)

data class ChangePasswordRequest(
    val password: String,
)

data class ChangeNicknameRequest(
    val nickname: String,
)
