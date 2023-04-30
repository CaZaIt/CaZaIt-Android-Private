package org.cazait.data.repository.users

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.data.Resource
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.SignUpRes
import org.cazait.data.remote.user.UserRemoteData
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val remoteData: UserRemoteData,
    private val ioDispatcher: CoroutineContext
) : UserRepository {
    override suspend fun signUp(body: SignUpReq): Flow<Resource<SignUpRes>> {
        return flow {
            emit(remoteData.postSignUp(body))
        }.flowOn(ioDispatcher)
    }

    override suspend fun isNicknameDup() {
        TODO("Not yet implemented")
    }

    override suspend fun isEmailDup() {
        TODO("Not yet implemented")
    }
}
