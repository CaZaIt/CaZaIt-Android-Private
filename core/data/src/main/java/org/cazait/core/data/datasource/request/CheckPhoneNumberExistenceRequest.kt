package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.PhoneNumber

data class CheckPhoneNumberExistenceRequest(
    val phoneNumber: String,
    val isExist: String,
) {
    constructor(
        phoneNumber: PhoneNumber,
        isExist: String,
    ) : this(phoneNumber = phoneNumber.toString(), isExist = isExist)
}
