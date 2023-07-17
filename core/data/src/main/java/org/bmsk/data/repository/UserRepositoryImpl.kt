package org.bmsk.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.bmsk.data.model.toEmailDup
import org.bmsk.data.model.toNicknameDup
import org.bmsk.data.model.toSignUpInfo
import org.cazait.datastore.data.repository.UserPreferenceRepository
import org.cazait.network.model.dto.request.IsIdNumberDupReq
import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.model.EmailDup
import org.cazait.model.NicknameDup
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.model.local.UserPreference
import org.cazait.network.datasource.UserRemoteData
import org.cazait.network.model.dto.DataResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val remoteData: UserRemoteData,
    private val ioDispatcher: CoroutineContext,
    private val userPreferenceRepository: UserPreferenceRepository,
) : UserRepository {

    override suspend fun signUp(
        idNumber: String,
        password: String,
        phoneNumber: String,
        nickname: String
    ): Flow<Resource<SignUpInfo>> {
        return flow {
            val body = SignUpReq(idNumber, password, phoneNumber, nickname)

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

    override suspend fun isIdNumberDup(idNumber: String): Flow<Resource<EmailDup>> {
        return flow {
            val body = IsIdNumberDupReq(idNumber)

            when (val response = remoteData.postIsIdNumberDup(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toEmailDup()))
                    } ?: emit(Resource.Error("잘못된 결과입니다."))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun isNicknameDup(nickname: String): Flow<Resource<NicknameDup>> {
        return flow {
            val body = IsNicknameDupReq(nickname)

            when (val response = remoteData.postIsNicknameDup(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toNicknameDup()))
                    } ?: emit(Resource.Error("잘못된 결과입니다."))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        val userPreference = userPreferenceRepository.getUserPreference().first()
        Log.e("UserRepository", "id = ${userPreference.id}")
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
