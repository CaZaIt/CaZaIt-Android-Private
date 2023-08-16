package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.model.SignUpCode
import org.cazait.model.Resource
import org.cazait.model.SignInInfo
import org.cazait.model.VerifyCode
import org.cazait.model.local.UserPreference

interface AuthRepository {
    suspend fun refreshToken(): Flow<Result<UserPreference>>
    suspend fun signIn(userId: String, password: String): Flow<Resource<SignInInfo>>
    suspend fun postSignUpCode(phoneNumber: String): Flow<Resource<SignUpCode>>
    suspend fun postVerifyCode(phoneNumber: String, verifyCode: Int): Flow<Resource<VerifyCode>>
    suspend fun postResetPasswordCode(userId: String, phoneNumber: String):Flow<Resource<SignUpCode>>
    suspend fun postFindIdCode(phoneNumber: String): Flow<Resource<SignUpCode>>
}
