package org.cazait.data.model.response

data class IsNicknameDupRes(
    val code: Int,
    val result: String,
    val message: String,
    val data: String,
)
