package org.bmsk.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.bmsk.data.model.toMessage
import org.bmsk.data.model.toSignInInfo
import org.bmsk.data.model.toVerify
import org.cazait.datastore.data.repository.UserPreferenceRepository
import org.cazait.datastore.data.repository.UserPreferenceRepository.TokenType.UPDATE_REFRESH_TOKEN
import org.cazait.model.Message
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.model.VerifyCode
import org.cazait.network.datasource.AuthRemoteData
import org.cazait.network.error.DEFAULT_ERROR
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.MessageReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.request.VerifyCodeReq
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteData: AuthRemoteData,
    private val ioDispatcher: CoroutineContext,
    private val userPreferenceRepository: UserPreferenceRepository
) : AuthRepository {
    override suspend fun refreshToken() {
        with(userPreferenceRepository.getUserPreference().first()) {
            val updatedRefreshToken = authRemoteData.getRefreshToken(
                userId = id,
                role = role,
                accessToken = accessToken,
                refreshToken = refreshToken
            ).data?.result ?: refreshToken

            userPreferenceRepository.updateUserToken(updatedRefreshToken, UPDATE_REFRESH_TOKEN)
        }
    }

    override suspend fun signIn(email: String, password: String): Flow<Resource<SignInInfo>> {
        return flow {
            val body = SignInReq(email, password)

            when (val response = authRemoteData.postSignIn(body)) {
                is DataResponse.Success -> {
                    val message = response.data?.message
                    val signInInfoDTO = response.data?.signInInfo

                    if (signInInfoDTO == null) {
                        emit(Resource.Error(message = message))
                    } else {
                        userPreferenceRepository.updateUserPreference(
                            isLoggedIn = true,
                            id = signInInfoDTO.id,
                            email = signInInfoDTO.email,
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
                    } else {
                        Log.e("AuthRepository", response.toString())
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun postMessage(phoneNumber: String): Flow<Resource<Message>> {
        return flow {
            val body = MessageReq(phoneNumber)
            when (val response = authRemoteData.postMessage(body)) {
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
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    private fun emptyInfo() = SignInInfo(
        email = "",
        id = "",
        accessToken = "",
        refreshToken = "",
        role = ""
    )
}
