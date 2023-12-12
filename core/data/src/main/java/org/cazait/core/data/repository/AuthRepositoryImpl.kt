package org.cazait.core.data.repository

import kotlinx.coroutines.flow.first
import org.cazait.core.data.datasource.AuthRemoteDataSource
import org.cazait.core.data.datasource.request.SignInRequest
import org.cazait.core.data.datasource.request.VerificationCodeRequest
import org.cazait.core.data.datasource.request.VerifyCodeRequest
import org.cazait.core.data.datasource.response.RefreshTokenResponse
import org.cazait.core.data.datasource.response.SignInResponse
import org.cazait.core.data.datasource.response.VerificationCodeResponse
import org.cazait.core.data.datasource.response.VerifyCodeResponse
import org.cazait.core.data.mapper.toData
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.repository.AuthRepository
import org.cazait.core.model.VerificationCode
import org.cazait.core.model.VerifyCode
import org.cazait.core.model.sign.SignInInfo
import org.cazait.core.model.token.RefreshToken
import org.cazait.datastore.data.repository.UserPreferenceRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun getRefreshToken(): NetworkResult<RefreshToken> {
        val userPreference = userPreferenceRepository.getUserPreference().first()
        return authRemoteDataSource.getRefreshToken(
            role = userPreference.role,
            refreshToken = userPreference.refreshToken,
        ).map(RefreshTokenResponse::toData)
    }

    override suspend fun postSignIn(
        userId: String,
        password: String,
    ): NetworkResult<SignInInfo> {
        val signInRequest = SignInRequest(userId, password)
        return authRemoteDataSource.postSignIn(
            signInRequest = signInRequest,
        ).map(SignInResponse::toData)
    }

    override suspend fun postVerifyCode(
        recipientPhoneNumber: String,
        verificationCode: Int,
    ): NetworkResult<VerifyCode> {
        val verifyCodeRequest = VerifyCodeRequest(recipientPhoneNumber, verificationCode)
        return authRemoteDataSource.postVerifyCode(
            verifyCodeRequest = verifyCodeRequest,
        ).map(VerifyCodeResponse::toData)
    }

    override suspend fun postVerificationCode(
        recipientPhoneNumber: String,
    ): NetworkResult<VerificationCode> {
        val verificationCodeRequest = VerificationCodeRequest(recipientPhoneNumber)
        return authRemoteDataSource.postVerificationCode(
            verificationCodeRequest = verificationCodeRequest,
        ).map(VerificationCodeResponse::toData)
    }
}
