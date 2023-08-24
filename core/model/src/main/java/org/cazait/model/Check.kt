package org.cazait.model

data class Check(
    val message: String,
    val data: String?
)

data class FindPassUserData(
    val message: String,
    val userId: String
)