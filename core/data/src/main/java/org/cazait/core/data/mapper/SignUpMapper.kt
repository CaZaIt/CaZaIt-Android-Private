package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.SignUpResponse
import org.cazait.core.model.SignUpInfo

internal fun SignUpResponse.toData(): SignUpInfo = SignUpInfo(
    uuid = this.signUpInfo.uuid,
    userId = this.signUpInfo.userId,
    phoneNumber = this.signUpInfo.phoneNumber,
    nickname = this.signUpInfo.nickname,
)
