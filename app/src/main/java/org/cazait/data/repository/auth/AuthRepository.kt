package org.cazait.data.repository.auth

import kotlinx.coroutines.flow.Flow
import org.cazait.data.Resource
import org.cazait.data.model.request.SignInReq
import org.cazait.data.model.response.SignInRes

interface AuthRepository {
    suspend fun refreshToken()
    suspend fun signIn(body: SignInReq): Flow<Resource<SignInRes>>
}