package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.SignInResponse
import org.cazait.core.model.sign.SignInInfo

internal fun SignInResponse.toData(): SignInInfo = SignInInfo(
    userId = this.signInInfo.userId,
    uuid = this.signInInfo.uuid,
    accessToken = this.signInInfo.accessToken,
    refreshToken = this.signInInfo.refreshToken,
    role = this.signInInfo.role,
)
