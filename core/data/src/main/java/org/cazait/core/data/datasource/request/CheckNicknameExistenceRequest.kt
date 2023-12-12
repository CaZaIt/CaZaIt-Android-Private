package org.cazait.core.data.datasource.request

import android.provider.ContactsContract.CommonDataKinds.Nickname

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
