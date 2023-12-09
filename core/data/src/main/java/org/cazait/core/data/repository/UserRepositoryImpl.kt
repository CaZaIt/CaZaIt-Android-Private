package org.cazait.core.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.core.data.datasource.request.ChangeNicknameRequest
import org.cazait.core.data.datasource.request.ChangePasswordRequest
import org.cazait.core.data.datasource.request.CheckNicknameRequest
import org.cazait.core.data.datasource.request.CheckPasswordRequest
import org.cazait.core.data.datasource.request.CheckPhoneNumRequest
import org.cazait.core.data.datasource.request.CheckUserDataRequest
import org.cazait.core.data.datasource.request.CheckUserIdRequest
import org.cazait.core.data.datasource.request.FindUserIdRequest
import org.cazait.core.data.datasource.request.ResetPasswordRequest
import org.cazait.core.data.datasource.request.SignUpRequest
import org.cazait.core.data.model.toCheck
import org.cazait.core.data.model.toFindUserId
import org.cazait.core.data.model.toResetPassword
import org.cazait.core.data.model.toSignUpInfo
import org.cazait.core.data.model.toUser
import org.cazait.datastore.data.repository.UserPreferenceRepository
import org.cazait.model.Check
import org.cazait.model.FindPassUserData
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.model.UserAccount
import org.cazait.model.UserPassword
import org.cazait.model.local.UserPreference
import org.cazait.network.error.EXIST_ACCOUNTNAME
import org.cazait.network.error.EXIST_PHONENUMBER
import org.cazait.network.error.INVALID_USER_PASSWORD
import org.cazait.network.model.dto.DataResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val ioDispatcher: CoroutineContext,
    private val userPreferenceRepository: UserPreferenceRepository,
) : UserRepository {

    override suspend fun signUp(
        userId: String,
        password: String,
        phoneNumber: String,
        nickname: String,
    ): Flow<Resource<SignUpInfo>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.SignUpRequest(
                userId,
                password,
                phoneNumber,
                nickname
            )

            when (val response = remoteData.postSignUp(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.signUpInfo.toSignUpInfo()))
                    } ?: emit(Resource.Error("잘못된 결과입니다."))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun checkPhoneNumDB(
        phoneNumber: String,
        isExist: String,
    ): Flow<Resource<String>> {
        return flow {
            val body =
                org.cazait.core.data.datasource.request.CheckPhoneNumRequest(phoneNumber, isExist)
            when (val response = remoteData.postCheckPhoneNum(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
                    }
                }

                is DataResponse.DataError -> {
                    Log.d("UserRepository 폰 Errorcode", response.toString())
                    if (response.errorCode == EXIST_PHONENUMBER) {
                        emit(Resource.Error(message = "이미 가입된 전화번호입니다"))
                    } else {
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    override suspend fun checkUserIdDB(userId: String, isExist: String): Flow<Resource<Check>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.CheckUserIdRequest(userId, isExist)
            when (val response = remoteData.postCheckUserId(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toCheck()))
                    }
                }

                is DataResponse.DataError -> {
                    Log.d("UserRepository 아이디 Errorcode", response.errorCode.toString())
                    if (response.errorCode == EXIST_ACCOUNTNAME) {
                        emit(Resource.Error(message = "이미 존재하는 아이디입니다."))
                    } else {
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    override suspend fun checkNicknameDB(
        nickname: String,
        isExist: String,
    ): Flow<Resource<String>> {
        return flow {
            val body =
                org.cazait.core.data.datasource.request.CheckNicknameRequest(nickname, isExist)
            when (val response = remoteData.postCheckNickname(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
                    }
                }

                is DataResponse.DataError -> {
                    Log.d("UserRepository 닉네임 Errorcode", response.errorCode.toString())
                    if (response.errorCode == EXIST_ACCOUNTNAME) {
                        emit(Resource.Error(message = "이미 존재하는 닉네임입니다."))
                    } else {
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    override suspend fun checkUserData(
        userUuid: String,
        phoneNumber: String,
    ): Flow<Resource<FindPassUserData>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.CheckUserDataRequest(phoneNumber)
            when (val response = remoteData.postCheckUserData(userUuid, body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toUser()))
                    }
                }

                is DataResponse.DataError -> {
                    Log.d("UserRepository 닉네임 Errorcode", response.errorCode.toString())
                }
            }
        }
    }

    override suspend fun findUserId(phoneNumber: String): Flow<Resource<UserAccount>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.FindUserIdRequest(phoneNumber)
            when (val response = remoteData.postFindUserId(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.data.toFindUserId()))
                    }
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }
    }

    override suspend fun resetPassword(
        userUuid: String,
        rePassword: String,
    ): Flow<Resource<UserPassword>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.ResetPasswordRequest(rePassword)
            when (val response = remoteData.patchPassword(userUuid, body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toResetPassword()))
                    }
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }
    }

    override suspend fun checkPassword(
        userUuid: String,
        rePassword: String,
    ): Flow<Resource<String>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.CheckPasswordRequest(rePassword)
            when (val response = remoteData.postCheckPassword(userUuid, body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
                    }
                }

                is DataResponse.DataError -> {
                    if (response.errorCode == 401) {
                        authRepository.refreshToken().first()

                        when (val newResponse = remoteData.postCheckPassword(userUuid, body)) {
                            is DataResponse.Success -> {
                                newResponse.data?.let {
                                    emit(Resource.Success(it.message))
                                }
                            }

                            is DataResponse.DataError -> {
                                if (newResponse.errorCode == INVALID_USER_PASSWORD) {
                                    emit(Resource.Error(message = "비밀번호가 올바르지 않습니다."))
                                } else {
                                    emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                                }
                            }
                        }
                    }
                    if (response.errorCode == INVALID_USER_PASSWORD) {
                        emit(Resource.Error(message = "비밀번호가 올바르지 않습니다."))
                    } else {
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    override suspend fun changePassword(
        userUuid: String,
        rePassword: String,
    ): Flow<Resource<String>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.ChangePasswordRequest(rePassword)
            when (val response = remoteData.patchChangePassword(userUuid, body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
                    }
                }

                is DataResponse.DataError -> {
                    if (response.errorCode == 401) {
                        authRepository.refreshToken().first()

                        when (val newResponse = remoteData.patchChangePassword(userUuid, body)) {
                            is DataResponse.Success -> {
                                newResponse.data?.let {
                                    emit(Resource.Success(it.message))
                                }
                            }

                            is DataResponse.DataError -> {
                                emit(Resource.Error(newResponse.toString()))
                            }
                        }
                    }
                    emit(Resource.Error(response.toString()))
                }
            }
        }
    }

    override suspend fun changeNickname(
        userUuid: String,
        reNickName: String,
    ): Flow<Resource<String>> {
        return flow {
            val body = org.cazait.core.data.datasource.request.ChangeNicknameRequest(reNickName)
            when (val response = remoteData.patchChangeNickname(userUuid, body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
                    }
                }

                is DataResponse.DataError -> {
                    if (response.errorCode == 401) {
                        authRepository.refreshToken().first()

                        when (val newResponse = remoteData.patchChangeNickname(userUuid, body)) {
                            is DataResponse.Success -> {
                                newResponse.data?.let {
                                    emit(Resource.Success(it.message))
                                }
                            }

                            is DataResponse.DataError -> {
                                emit(Resource.Error(newResponse.toString()))
                            }
                        }
                    }
                    emit(Resource.Error(response.toString()))
                }
            }
        }
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        val userPreference = userPreferenceRepository.getUserPreference().first()
        Log.e("UserRepository", "id = ${userPreference.uuid}")
        return flow { emit(userPreference.isLoggedIn) }
    }

    override suspend fun getUserInfo(): Flow<UserPreference> {
        return flow {
            emit(userPreferenceRepository.getUserPreference().first())
        }
    }

    override suspend fun signOut() {
        userPreferenceRepository.clearUserPreference()
    }
}
