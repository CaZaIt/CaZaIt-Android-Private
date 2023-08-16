package org.bmsk.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.bmsk.data.model.toFindUserId
import org.bmsk.data.model.toResetPassword
import org.bmsk.data.model.toSignUpInfo
import org.cazait.datastore.data.repository.UserPreferenceRepository
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.model.UserAccount
import org.cazait.model.UserPassword
import org.cazait.model.local.UserPreference
import org.cazait.network.datasource.UserRemoteData
import org.cazait.network.error.EXIST_ACCOUNTNAME
import org.cazait.network.error.NOT_EXIST_USER
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.CheckNicknameReq
import org.cazait.network.model.dto.request.CheckPhoneNumReq
import org.cazait.network.model.dto.request.CheckUserIdReq
import org.cazait.network.model.dto.request.FindUserIdReq
import org.cazait.network.model.dto.request.ResetPasswordReq
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val remoteData: UserRemoteData,
    private val ioDispatcher: CoroutineContext,
    private val userPreferenceRepository: UserPreferenceRepository,
) : UserRepository {

    override suspend fun signUp(
        userId: String,
        password: String,
        phoneNumber: String,
        nickname: String
    ): Flow<Resource<SignUpInfo>> {
        return flow {
            val body = SignUpReq(userId, password, phoneNumber, nickname)

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
        isExist: String
    ): Flow<Resource<String>> {
        return flow {
            val body = CheckPhoneNumReq(phoneNumber, isExist)
            when(val response = remoteData.postCheckPhoneNum(body)){
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
                    }
                }
                is DataResponse.DataError -> {
                    Log.d("UserRepository 폰 Errorcode", response.errorCode.toString())
                    if (response.errorCode == NOT_EXIST_USER) {
                        emit(Resource.Error(message = "존재하지 않는 유저입니다."))
                    } else {
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    override suspend fun checkUserIdDB(userId: String, isExist: String): Flow<Resource<String>> {
        return flow {
            val body = CheckUserIdReq(userId, isExist)
            when(val response = remoteData.postCheckUserId(body)){
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
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
        isExist: String
    ): Flow<Resource<String>> {
        return flow {
            val body = CheckNicknameReq(nickname, isExist)
            when(val response = remoteData.postCheckNickname(body)){
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.message))
                    }
                }
                is DataResponse.DataError -> {
                    Log.d("UserRepository 닉네임 Errorcode", response.errorCode.toString())
                    if (response.errorCode == EXIST_ACCOUNTNAME) {
                        emit(Resource.Error(message = "이미 존재하는 아이디입니다."))
                    } else {
                        emit(Resource.Error(message = "알 수 없는 에러가 발생했습니다."))
                    }
                }
            }
        }
    }

    override suspend fun findUserId(phoneNumber: String): Flow<Resource<UserAccount>> {
        return flow {
            val body = FindUserIdReq(phoneNumber)
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
        phoneNumber: String,
        rePassword: String
    ): Flow<Resource<UserPassword>> {
        return flow {
            val body = ResetPasswordReq(phoneNumber, rePassword)
            when (val response = remoteData.patchPassword(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.data.toResetPassword()))
                    }
                }

                is DataResponse.DataError -> {
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
