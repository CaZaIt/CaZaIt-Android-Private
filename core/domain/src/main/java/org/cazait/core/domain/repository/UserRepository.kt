package org.cazait.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.AccountName
import org.cazait.core.domain.model.user.Password
import org.cazait.core.model.ExistenceStatus
import org.cazait.core.model.FindPassUserData
import org.cazait.core.model.SignUpInfo
import org.cazait.core.model.UserAccount
import org.cazait.core.model.UserPassword
import org.cazait.core.model.local.UserPreference

interface UserRepository {
    suspend fun signUp(
        accountName: AccountName,
        password: Password,
        phoneNumber: String,
        nickname: String,
    ): NetworkResult<SignUpInfo>

    suspend fun postCheckPhoneNumberExistence(
        phoneNumber: String,
        isExist: String,
    ): NetworkResult<ExistenceStatus>

    suspend fun postCheckAccountNameExistence(
        accountName: String,
        isExist: String,
    ): NetworkResult<ExistenceStatus>

    suspend fun postCheckNicknameExistence(
        nickname: String,
        isExist: String,
    ): NetworkResult<ExistenceStatus>

    suspend fun checkUserData(
        userId: String,
        phoneNumber: String,
    ): NetworkResult<FindPassUserData>

    suspend fun findUserId(
        userPhoneNumber: String,
    ): NetworkResult<UserAccount>

    suspend fun resetPassword(
        userId: String,
        password: String,
    ): NetworkResult<UserPassword>

    suspend fun checkPassword(
        userId: String,
        password: String,
    ): NetworkResult<String>

    suspend fun changePassword(
        userId: String,
        password: String,
    ): NetworkResult<String>

    suspend fun changeNickname(
        userId: String,
        nickname: String,
    ): NetworkResult<String>

    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun getUserInfo(): Flow<UserPreference>
    suspend fun signOut()
}
