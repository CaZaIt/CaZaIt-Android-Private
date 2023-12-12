package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.AccountName
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.PhoneNumber

class SignUpRequest private constructor(
    val accountName: String,
    val password: String,
    val phoneNumber: String,
    val nickname: String,
) {
    companion object {
        fun of(
            accountName: AccountName,
            password: Password,
            phoneNumber: PhoneNumber,
            nickname: Nickname,
        ): SignUpRequest =
            SignUpRequest(
                accountName = accountName.toString(),
                password = password.toString(),
                phoneNumber = phoneNumber.toString(),
                nickname = nickname.toString(),
            )
    }
}
