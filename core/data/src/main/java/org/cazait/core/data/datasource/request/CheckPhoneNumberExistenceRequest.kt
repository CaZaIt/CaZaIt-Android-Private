package org.cazait.core.data.datasource.request

data class CheckPhoneNumberExistenceRequest(
    val phoneNumber: String,
    val isExist: String,
)
