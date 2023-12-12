package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.PhoneNumber

class FindUserIdRequest private constructor(
    val userPhoneNumber: String,
) {
    companion object {
        fun of(phoneNumber: PhoneNumber): FindUserIdRequest =
            FindUserIdRequest(phoneNumber.toString())
    }
}

class CheckUserDataRequest private constructor(
    val phoneNumber: String,
) {
    companion object {
        fun of(phoneNumber: PhoneNumber): CheckUserDataRequest =
            CheckUserDataRequest(phoneNumber.toString())
    }
}
