package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.model.Resource
import org.cazait.model.SignInInfo

interface AuthRepository {
    suspend fun refreshToken()
    suspend fun signIn(email: String, password: String): Flow<Resource<SignInInfo>>
}
