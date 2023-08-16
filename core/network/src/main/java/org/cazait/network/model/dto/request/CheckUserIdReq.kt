package org.cazait.network.model.dto.request

data class CheckUserIdReq(
    val accountName: String,
    val isExist: String
)
