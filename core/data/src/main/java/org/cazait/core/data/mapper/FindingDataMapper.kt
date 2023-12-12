package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.CheckUserDataResponse
import org.cazait.core.data.datasource.response.FindUserIdResponse
import org.cazait.core.data.datasource.response.ResetPasswordResponse
import org.cazait.core.model.FindPassUserData
import org.cazait.core.model.UserAccount
import org.cazait.core.model.UserPassword

internal fun CheckUserDataResponse.toData(): FindPassUserData = FindPassUserData(
    message = this.message,
    userId = this.data.userId,
)

internal fun FindUserIdResponse.toData(): UserAccount = UserAccount(
    userId = this.data.userId,
)

internal fun ResetPasswordResponse.toData(): UserPassword = UserPassword(
    message = this.message,
)
