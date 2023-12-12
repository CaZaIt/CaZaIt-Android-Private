package org.cazait.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.core.domain.model.network.Message
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.AccountName
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.PhoneNumber
import org.cazait.core.domain.model.user.UserId
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
        phoneNumber: PhoneNumber,
        nickname: Nickname,
    ): NetworkResult<SignUpInfo>

    suspend fun postCheckPhoneNumberExistence(
        phoneNumber: PhoneNumber,
        isExist: String,
    ): NetworkResult<ExistenceStatus>

    suspend fun postCheckAccountNameExistence(
        accountName: AccountName,
        isExist: String,
    ): NetworkResult<ExistenceStatus>

    suspend fun postCheckNicknameExistence(
        nickname: Nickname,
        isExist: String,
    ): NetworkResult<ExistenceStatus>

    suspend fun checkUserData(
        userId: UserId,
        phoneNumber: PhoneNumber,
    ): NetworkResult<FindPassUserData>

    suspend fun findUserId(
        userPhoneNumber: PhoneNumber,
    ): NetworkResult<UserAccount>

    suspend fun resetPassword(
        userId: UserId,
        password: Password,
    ): NetworkResult<UserPassword>

    suspend fun checkPassword(
        userId: UserId,
        password: Password,
    ): NetworkResult<Message>

    suspend fun changePassword(
        userId: UserId,
        password: Password,
    ): NetworkResult<Message>

    suspend fun changeNickname(
        userId: UserId,
        nickname: Nickname,
    ): NetworkResult<Message>

    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun getUserInfo(): Flow<UserPreference>
    suspend fun signOut()
}
