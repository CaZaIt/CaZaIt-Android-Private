package org.cazait.core.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.core.data.model.toMessage
import org.cazait.core.data.model.toSignInInfo
import org.cazait.core.data.model.toVerify
import org.cazait.datastore.data.repository.UserPreferenceRepository
import org.cazait.model.VerificationCode
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.model.VerifyCode
import org.cazait.network.datasource.AuthRemoteData
import org.cazait.network.error.DEFAULT_ERROR
import org.cazait.network.error.EXPIRED_VERIFICATION_CODE
import org.cazait.network.error.FAILED_TO_LOGIN
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.VerificationCodeReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.request.VerifyCodeReq
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteData: AuthRemoteData,
    private val ioDispatcher: CoroutineContext,
    private val userPreferenceRepository: UserPreferenceRepository
) : AuthRepository {
    override suspend fun refreshToken() = flow {
        val userPreference = userPreferenceRepository.getUserPreference().first()

        val updatedRefreshToken = authRemoteData.getRefreshToken(
            role = userPreference.role,
            refreshToken = userPreference.refreshToken
        ).data
        val accessToken = updatedRefreshToken?.data?.accessToken ?: userPreference.accessToken
        val refreshToken = updatedRefreshToken?.data?.refreshToken ?: userPreference.refreshToken
        Log.d("재발급된 accessToken", accessToken)
        Log.d("재발급된 refreshToken", refreshToken)

        emit(
            userPreferenceRepository.updateUserToken(
                accessToken,
                refreshToken
            )
        )
    }

    override suspend fun signIn(userId: String, password: String): Flow<Resource<SignInInfo>> {
        return flow {
            val body = SignInReq(userId, password)

            when (val response = authRemoteData.postSignIn(body)) {
                is DataResponse.Success -> {
                    val message = response.data?.message
                    val signInInfoDTO = response.data?.signInInfo

                    if (signInInfoDTO == null) {
                        emit(Resource.Error(message = message))
                    } else {
                        userPreferenceRepository.updateUserPreference(
                            isLoggedIn = true,
                            uuid = signInInfoDTO.uuid,
                            userId = signInInfoDTO.userId,
                            role = signInInfoDTO.role,
                            accessToken = signInInfoDTO.accessToken,
                            refreshToken = signInInfoDTO.refreshToken,
                        )
                        emit(Resource.Success(signInInfoDTO.toSignInInfo()))
                    }
                }

                is DataResponse.DataError -> {
                    if (response.errorCode == DEFAULT_ERROR) {
                        emit(Resource.Error(message = "네트워크 상태를 확인해주세요."))
                    } else if (response.errorCode == FAILED_TO_LOGIN) {
                        emit(Resource.Error(message = "아이디 또는 비밀번호를 잘못 입력했습니다."))
                    } else {
                        Log.e("AuthRepository", response.toString())
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun postVerifyCode(
        phoneNumber: String,
        verifyCode: Int
    ): Flow<Resource<VerifyCode>> {
        return flow {
            val body = VerifyCodeReq(phoneNumber, verifyCode)
            when (val response = authRemoteData.postVerifyCode(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toVerify()))
                    } ?: emit(Resource.Error("잘못된 결과입니다."))
                }

                is DataResponse.DataError -> {
                    if (response.errorCode == EXPIRED_VERIFICATION_CODE) {
                        emit(Resource.Error(message = "만료된 인증번호입니다."))
                    } else {
                        Log.e("AuthRepository", response.toString())
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun postVerificationCode(phoneNumber: String): Flow<Resource<VerificationCode>> {
        return flow {
            val body = VerificationCodeReq(phoneNumber)
            when (val response = authRemoteData.postVerificationCode(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toMessage()))
                    } ?: emit(Resource.Error("잘못된 결과입니다."))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    private fun emptyInfo() = SignInInfo(
        userId = "",
        uuid = "",
        accessToken = "",
        refreshToken = "",
        role = ""
    )
}
