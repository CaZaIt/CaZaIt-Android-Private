package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.AccountName

class CheckUserIdRequest private constructor(
    val accountName: String,
    val isExist: String,
) {
    companion object {
        fun of(accountName: AccountName, isExist: String): CheckUserIdRequest =
            CheckUserIdRequest(accountName = accountName.toString(), isExist = isExist)
    }
}
