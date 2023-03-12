package org.cazait.data.model.response

data class IsEmailDupRes(
    val code: Int,
    val result: String,
    val message: String,
    val data: String,
)
