package org.cazait.data.repository.users

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.data.dto.request.IsEmailDupReq
import org.cazait.data.dto.request.IsNicknameDupReq
import org.cazait.data.dto.request.SignUpReq
import org.cazait.data.dto.response.DataResponse
import org.cazait.data.source.remote.user.UserRemoteData
import org.cazait.domain.model.EmailDup
import org.cazait.domain.model.NicknameDup
import org.cazait.domain.model.Resource
import org.cazait.domain.model.SignUp
import org.cazait.domain.model.mapper.DomainMapper.toEmailDup
import org.cazait.domain.model.mapper.DomainMapper.toNicknameDup
import org.cazait.domain.model.mapper.DomainMapper.toSignUp
import org.cazait.domain.repository.UserRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val remoteData: UserRemoteData,
    private val ioDispatcher: CoroutineContext
) : UserRepository {
    override suspend fun signUp(body: SignUpReq): Flow<Resource<SignUp>> {
        return flow {
            when (val response = remoteData.postSignUp(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toSignUp()))
                    } ?: emit(Resource.Error(response.toString()))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun isNicknameDup(body: IsNicknameDupReq): Flow<Resource<NicknameDup>> {
        return flow {
            when (val response = remoteData.postIsNicknameDup(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toNicknameDup()))
                    } ?: emit(Resource.Error(response.toString()))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun isEmailDup(body: IsEmailDupReq): Flow<Resource<EmailDup>> {
        return flow {
            when (val response = remoteData.postIsEmailDup(body)) {
                is DataResponse.Success -> {
                    response.data?.let {
                        emit(Resource.Success(it.toEmailDup()))
                    } ?: emit(Resource.Error("데이터가 존재하지 않음"))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }
}
