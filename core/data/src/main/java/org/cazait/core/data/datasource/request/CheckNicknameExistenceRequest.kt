package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.Nickname

data class CheckNicknameExistenceRequest(
    val nickname: String,
    val isExist: String,
) {
    constructor(nickname: Nickname, isExist: String) : this(
        nickname = nickname.toString(),
        isExist = isExist,
    )
}
