package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.Password

class CheckPasswordRequest private constructor(
    val password: String,
) {
    companion object {
        fun of(password: Password): CheckPasswordRequest =
            CheckPasswordRequest(password = password.toString())
    }
}

class ChangePasswordRequest private constructor(
    val password: String,
) {
    companion object {
        fun of(password: Password): ChangePasswordRequest =
            ChangePasswordRequest(password = password.toString())
    }
}

class ChangeNicknameRequest private constructor(
    val nickname: String,
) {
    companion object {
        fun of(nickname: Nickname): ChangeNicknameRequest =
            ChangeNicknameRequest(nickname = nickname.toString())
    }
}
