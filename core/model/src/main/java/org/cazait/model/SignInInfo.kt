package org.cazait.model

import java.util.UUID

data class SignInInfo(
    val userId: String,
    val uuid: String,
    val accessToken: String,
    val refreshToken: String,
    val role: String,
)
