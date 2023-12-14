package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.Password

@JvmInline
value class ChangePasswordRequest(
    private val password: String,
)

class ChangeNicknameRequest(
    val nickname: String,
) {
    constructor(nickname: Nickname) : this(nickname = nickname.toString())
}
