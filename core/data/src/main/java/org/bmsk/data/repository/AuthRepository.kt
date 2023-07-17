package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.model.Message
import org.cazait.model.Resource
import org.cazait.model.SignInInfo

interface AuthRepository {
    suspend fun refreshToken()
    suspend fun signIn(email: String, password: String): Flow<Resource<SignInInfo>>
    suspend fun postMessage(phoneNumber: String): Flow<Resource<Message>>
}
