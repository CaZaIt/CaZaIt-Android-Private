package org.cazait.network.model.dto.request

data class CheckNicknameReq(
    val nickname: String,
    val isExist: String
)
