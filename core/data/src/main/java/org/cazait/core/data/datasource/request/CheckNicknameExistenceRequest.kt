package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.Nickname

class CheckNicknameExistenceRequest private constructor(
    val nickname: String,
    val isExist: String,
) {
    companion object {
        fun of(nickname: Nickname, isExist: String): CheckNicknameExistenceRequest =
            CheckNicknameExistenceRequest(
                nickname = nickname.toString(),
                isExist = isExist,
            )
    }
}
