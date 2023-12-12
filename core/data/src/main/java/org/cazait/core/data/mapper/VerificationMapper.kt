package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.VerificationCodeResponse
import org.cazait.core.data.datasource.response.VerifyCodeResponse
import org.cazait.core.model.VerificationCode
import org.cazait.core.model.VerifyCode

internal fun VerifyCodeResponse.toData(): VerifyCode = VerifyCode(
    verify = false,
    message = this.message,
)

internal fun VerificationCodeResponse.toData(): VerificationCode = VerificationCode(
    verify = false,
    message = this.message,
)
