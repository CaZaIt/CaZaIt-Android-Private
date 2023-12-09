package org.cazait.core.data.datasource.request

data class SignUpRequest(
    val userId: String,
    val password: String,
    val phoneNumber: String,
    val nickname: String,
)
