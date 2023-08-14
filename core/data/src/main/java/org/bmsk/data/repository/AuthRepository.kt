package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.model.Message
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.model.VerifyCode
import org.cazait.model.local.UserPreference

interface AuthRepository {
    suspend fun refreshToken(): Flow<Result<UserPreference>>
    suspend fun signIn(userId: String, password: String): Flow<Resource<SignInInfo>>
    suspend fun postSignUpCode(phoneNumber: String): Flow<Resource<Message>>
    suspend fun postVerifyCode(phoneNumber: String, verifyCode: Int): Flow<Resource<VerifyCode>>
}
