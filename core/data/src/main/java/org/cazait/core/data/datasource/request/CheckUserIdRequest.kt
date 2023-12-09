package org.cazait.core.data.datasource.request

data class CheckUserIdRequest(
    val accountName: String,
    val isExist: String,
)
