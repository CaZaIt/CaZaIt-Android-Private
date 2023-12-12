package org.cazait.core.data.datasource

import org.cazait.core.data.api.auth.TokenApi
import org.cazait.core.data.api.unauth.AuthApi
import org.cazait.core.data.datasource.request.SignInRequest
import org.cazait.core.data.datasource.request.VerificationCodeRequest
import org.cazait.core.data.datasource.request.VerifyCodeRequest
import org.cazait.core.data.datasource.response.RefreshTokenResponse
import org.cazait.core.data.datasource.response.SignInResponse
import org.cazait.core.data.datasource.response.VerificationCodeResponse
import org.cazait.core.data.datasource.response.VerifyCodeResponse
import org.cazait.core.data.di.Authenticated
import org.cazait.core.domain.model.network.NetworkResult
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    @Authenticated private val tokenApi: TokenApi,
) {
    suspend fun getRefreshToken(
        role: String,
        refreshToken: String,
    ): NetworkResult<RefreshTokenResponse> =
        tokenApi.getRefreshTokenAuth(role, refreshToken)

    suspend fun postSignIn(
        signInRequest: SignInRequest,
    ): NetworkResult<SignInResponse> =
        authApi.postSignIn(signInRequest = signInRequest)

    suspend fun postVerifyCode(
        verifyCodeRequest: VerifyCodeRequest,
    ): NetworkResult<VerifyCodeResponse> =
        authApi.postVerifyCode(verifyCodeRequest = verifyCodeRequest)

    suspend fun postVerificationCode(
        verificationCodeRequest: VerificationCodeRequest,
    ): NetworkResult<VerificationCodeResponse> =
        authApi.postVerificationCode(verificationCodeRequest = verificationCodeRequest)
}
