package org.cazait.core.domain.repository

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.PhoneNumber
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.model.VerificationCode
import org.cazait.core.model.VerifyCode
import org.cazait.core.model.sign.SignInInfo
import org.cazait.core.model.token.RefreshToken

interface AuthRepository {
    suspend fun getRefreshToken(): NetworkResult<RefreshToken>

    suspend fun postSignIn(
        userId: UserId,
        password: Password,
    ): NetworkResult<SignInInfo>

    suspend fun postVerifyCode(
        recipientPhoneNumber: String,
        verificationCode: Int,
    ): NetworkResult<VerifyCode>

    suspend fun postVerificationCode(
        recipientPhoneNumber: PhoneNumber,
    ): NetworkResult<VerificationCode>
}
