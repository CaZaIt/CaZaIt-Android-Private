package org.cazait.core.data.datasource.request

data class CheckNicknameExistenceRequest(
    val nickname: String,
    val isExist: String,
)
