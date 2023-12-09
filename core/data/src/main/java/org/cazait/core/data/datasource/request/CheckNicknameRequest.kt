package org.cazait.core.data.datasource.request

data class CheckNicknameRequest(
    val nickname: String,
    val isExist: String,
)
