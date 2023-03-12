package org.cazait.data.model.response

data class SignUpRes(
    val code: Int,
    val result: String,
    val message: String,
    val data: Data,
) {
    data class Data(
        val id: Long,
        val email: String,
        val password: String,
        val nickname: String,
    )
}
