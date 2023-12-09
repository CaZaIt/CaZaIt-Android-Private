package org.cazait.core.data.datasource.request

data class CheckPhoneNumRequest(
    val phoneNumber: String,
    val isExist: String,
)
