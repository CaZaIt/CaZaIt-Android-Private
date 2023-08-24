package org.cazait.network.model.dto.request

data class CheckPasswordReq(
    val password: String
)

data class ChangePasswordReq(
    val password: String
)

data class ChangeNicknameReq(
    val nickname: String
)