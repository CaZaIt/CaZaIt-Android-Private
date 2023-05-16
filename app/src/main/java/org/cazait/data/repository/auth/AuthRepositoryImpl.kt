package org.cazait.data.repository.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.cazait.domain.model.Resource
import org.cazait.data.dto.request.SignInReq
import org.cazait.data.dto.response.SignInRes
import org.cazait.data.source.remote.auth.AuthRemoteData
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthRepositoryImpl @Inject constructor(
    private val remoteData: AuthRemoteData,
    private val ioDispatcher: CoroutineContext,
) : AuthRepository {
    override suspend fun refreshToken() {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(body: SignInReq): Flow<Resource<SignInRes>> {
        return flow {
            emit(remoteData.postSignIn(body))
        }.flowOn(ioDispatcher)
    }
}
