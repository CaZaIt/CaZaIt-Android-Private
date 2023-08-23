package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.model.Check
import org.cazait.model.FindPassUserData
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.model.UserAccount
import org.cazait.model.UserPassword
import org.cazait.model.local.UserPreference

interface UserRepository {
    suspend fun signUp(
        userId: String,
        password: String,
        phoneNumber: String,
        nickname: String
    ): Flow<Resource<SignUpInfo>>

    suspend fun checkPhoneNumDB(phoneNumber: String, isExist: String): Flow<Resource<String>>
    suspend fun checkUserIdDB(userId: String, isExist: String): Flow<Resource<Check>>
    suspend fun checkNicknameDB(nickname: String, isExist: String): Flow<Resource<String>>
    suspend fun checkUserData(userUuid: String, phoneNumber: String): Flow<Resource<FindPassUserData>>
    suspend fun findUserId(phoneNumber: String): Flow<Resource<UserAccount>>
    suspend fun resetPassword(userUuid: String, rePassword: String): Flow<Resource<UserPassword>>
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun getUserInfo(): Flow<UserPreference>
    suspend fun signOut()
}
