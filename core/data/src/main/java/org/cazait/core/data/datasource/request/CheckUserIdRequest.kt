package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.AccountName

data class CheckUserIdRequest(
    val accountName: String,
    val isExist: String,
) {
    constructor(
        accountName: AccountName,
        isExist: String,
    ) : this(accountName = accountName.toString(), isExist = isExist)
}
