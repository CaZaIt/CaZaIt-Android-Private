package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.PhoneNumber

class CheckPhoneNumberExistenceRequest private constructor(
    val phoneNumber: String,
    val isExist: String,
) {
    companion object {
        fun of(phoneNumber: PhoneNumber, isExist: String): CheckPhoneNumberExistenceRequest =
            CheckPhoneNumberExistenceRequest(
                phoneNumber = phoneNumber.toString(),
                isExist = isExist,
            )
    }
}
