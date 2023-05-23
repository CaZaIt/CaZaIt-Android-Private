package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.bmsk.data.model.toEmailDup
import org.bmsk.data.model.toNicknameDup
import org.bmsk.data.model.toSignUpInfo
import org.cazait.network.model.dto.request.IsEmailDupReq
import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.model.EmailDup
import org.cazait.model.NicknameDup
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.network.datasource.UserRemoteData
import org.cazait.network.model.dto.DataResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val remoteData: UserRemoteData,
    private val ioDispatcher: CoroutineContext
) : UserRepository {
    override suspend fun signUp(email: String, password: String, nickname: String): Flow<Resource<SignUpInfo>> {
        return flow {
            val body = SignUpReq(email, password, nickname)

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

    override suspend fun isEmailDup(email: String): Flow<Resource<EmailDup>> {
        return flow {
            val body = IsEmailDupReq(email)

            when (val response = remoteData.postIsEmailDup(body)) {
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
}
