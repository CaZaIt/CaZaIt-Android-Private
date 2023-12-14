package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.PhoneNumber

data class FindUserIdRequest(
    val userPhoneNumber: String,
) {
    constructor(phoneNumber: PhoneNumber) : this(userPhoneNumber = phoneNumber.toString())
}

class CheckUserDataRequest private constructor(
    val phoneNumber: String,
) {
    constructor(phoneNumber: PhoneNumber) : this(phoneNumber = phoneNumber.toString())
}
