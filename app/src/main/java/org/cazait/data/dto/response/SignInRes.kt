package org.cazait.data.dto.response

data class SignInRes(
    val code: Int,
    val data: Data,
    val message: String,
    val result: String,
) {
    data class Data(
        val email: String,
        val id: Long,
        val jwtToken: String,
        val refreshToken: String,
        val role: String,
    )
}
