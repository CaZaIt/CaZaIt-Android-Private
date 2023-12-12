package org.cazait.core.model

data class ExistenceStatus(
    val message: String,
)

data class FindPassUserData(
    val message: String,
    val userId: String,
)
