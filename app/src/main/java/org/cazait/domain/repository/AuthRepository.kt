package org.cazait.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.domain.model.Resource
import org.cazait.data.dto.request.SignInReq
import org.cazait.data.dto.response.SignInRes

interface AuthRepository {
    suspend fun refreshToken()
    suspend fun signIn(body: SignInReq): Flow<Resource<SignInRes>>
}
