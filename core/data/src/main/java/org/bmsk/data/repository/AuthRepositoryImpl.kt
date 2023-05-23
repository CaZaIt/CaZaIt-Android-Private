package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.bmsk.data.model.toSignInInfo
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.network.datasource.AuthRemoteData
import org.cazait.network.model.dto.DataResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteData: AuthRemoteData,
    private val ioDispatcher: CoroutineContext,
) : AuthRepository {
    override suspend fun refreshToken() {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String): Flow<Resource<SignInInfo>> {
        return flow {
            val body = SignInReq(email, password)

            when (val response = authRemoteData.postSignIn(body)) {
                is DataResponse.Success -> {
                    response.data?.signInInfo?.toSignInInfo()?.let {
                        emit(Resource.Success(it))
                    }?: emit(Resource.Error(response.toString()))
                }

                is DataResponse.DataError -> {
                    emit(Resource.Error(response.toString()))
                }
            }
        }.flowOn(ioDispatcher)
    }
}
