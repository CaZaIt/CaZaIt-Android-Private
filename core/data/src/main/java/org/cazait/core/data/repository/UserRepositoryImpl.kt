package org.cazait.core.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.cazait.core.data.datasource.UserRemoteDataSource
import org.cazait.core.data.datasource.request.ChangeNicknameRequest
import org.cazait.core.data.datasource.request.ChangePasswordRequest
import org.cazait.core.data.datasource.request.CheckNicknameExistenceRequest
import org.cazait.core.data.datasource.request.CheckPasswordRequest
import org.cazait.core.data.datasource.request.CheckPhoneNumberExistenceRequest
import org.cazait.core.data.datasource.request.CheckUserDataRequest
import org.cazait.core.data.datasource.request.CheckUserIdRequest
import org.cazait.core.data.datasource.request.FindUserIdRequest
import org.cazait.core.data.datasource.request.ResetPasswordRequest
import org.cazait.core.data.datasource.request.SignUpRequest
import org.cazait.core.data.datasource.response.CheckUserDataResponse
import org.cazait.core.data.datasource.response.ExistenceResponse
import org.cazait.core.data.datasource.response.FindUserIdResponse
import org.cazait.core.data.datasource.response.ResetPasswordResponse
import org.cazait.core.data.datasource.response.SignUpResponse
import org.cazait.core.data.mapper.toData
import org.cazait.core.domain.model.Message
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.AccountName
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.PhoneNumber
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.ExistenceStatus
import org.cazait.core.model.FindPassUserData
import org.cazait.core.model.SignUpInfo
import org.cazait.core.model.UserAccount
import org.cazait.core.model.UserPassword
import org.cazait.core.model.local.UserPreference
import org.cazait.datastore.data.repository.UserPreferenceRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userPreferenceRepository: UserPreferenceRepository,
) : UserRepository {

    override suspend fun signUp(
        accountName: AccountName,
        password: Password,
        phoneNumber: PhoneNumber,
        nickname: Nickname,
    ): NetworkResult<SignUpInfo> {
        val request = SignUpRequest.of(
            accountName = accountName,
            password = password,
            phoneNumber = phoneNumber,
            nickname = nickname,
        )
        return userRemoteDataSource.postSignUp(signUpRequest = request)
            .map(SignUpResponse::toData)
    }

    override suspend fun postCheckPhoneNumberExistence(
        phoneNumber: PhoneNumber,
        isExist: String,
    ): NetworkResult<ExistenceStatus> {
        val request = CheckPhoneNumberExistenceRequest.of(phoneNumber, isExist)
        return userRemoteDataSource.postCheckPhoneNumberExistence(
            checkPhoneNumRequest = request,
        ).map(ExistenceResponse::toData)
    }

    override suspend fun postCheckAccountNameExistence(
        accountName: AccountName,
        isExist: String,
    ): NetworkResult<ExistenceStatus> {
        val checkUserIdRequest = CheckUserIdRequest.of(accountName, isExist)
        return userRemoteDataSource.postCheckAccountNameExistence(
            checkUserIdRequest = checkUserIdRequest,
        ).map(ExistenceResponse::toData)
    }

    override suspend fun postCheckNicknameExistence(
        nickname: Nickname,
        isExist: String,
    ): NetworkResult<ExistenceStatus> {
        val checkNicknameRequest = CheckNicknameExistenceRequest.of(nickname, isExist)
        return userRemoteDataSource.postCheckNicknameExistence(
            checkNicknameRequest,
        ).map(ExistenceResponse::toData)
    }

    override suspend fun checkUserData(
        userId: String,
        phoneNumber: String,
    ): NetworkResult<FindPassUserData> {
        val checkUserDataRequest = CheckUserDataRequest(phoneNumber)
        return userRemoteDataSource.postCheckUserData(
            userUuid = userId,
            checkUserDataRequest = checkUserDataRequest,
        ).map(CheckUserDataResponse::toData)
    }

    override suspend fun findUserId(
        userPhoneNumber: String,
    ): NetworkResult<UserAccount> {
        val findUserIdRequest = FindUserIdRequest(userPhoneNumber)
        return userRemoteDataSource.postFindUserId(findUserIdRequest = findUserIdRequest)
            .map(FindUserIdResponse::toData)
    }

    override suspend fun resetPassword(
        userId: String,
        password: String,
    ): NetworkResult<UserPassword> {
        val resetPasswordRequest = ResetPasswordRequest(password)
        return userRemoteDataSource.patchPassword(
            userUuid = userId,
            resetPasswordRequest = resetPasswordRequest,
        ).map(ResetPasswordResponse::toData)
    }

    override suspend fun checkPassword(
        userId: UserId,
        password: Password,
    ): NetworkResult<Message> {
        val checkPasswordRequest = CheckPasswordRequest.of(password)
        return userRemoteDataSource.postCheckPassword(
            userId = userId.toUUID(),
            checkPasswordRequest = checkPasswordRequest,
        ).map { Message(it.message) }
    }

    override suspend fun changePassword(
        userId: UserId,
        password: Password,
    ): NetworkResult<Message> {
        val changePasswordRequest = ChangePasswordRequest.of(password)
        return userRemoteDataSource.patchChangePassword(
            userId = userId.toUUID(),
            changePasswordRequest = changePasswordRequest,
        ).map { Message(it.message) }
    }

    override suspend fun changeNickname(
        userId: UserId,
        nickname: Nickname,
    ): NetworkResult<Message> {
        val changeNicknameRequest = ChangeNicknameRequest.of(nickname)
        return userRemoteDataSource.patchChangeNickname(
            userId = userId.toUUID(),
            changeNicknameRequest = changeNicknameRequest,
        ).map { Message(it.message) }
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        val userPreference = userPreferenceRepository.getUserPreference().first()
        Log.e("UserRepository", "id = ${userPreference.userId}")
        return flow { emit(userPreference.isLoggedIn) }
    }

    override suspend fun getUserInfo(): Flow<UserPreference> {
        return flow {
            emit(userPreferenceRepository.getUserPreference().first())
        }
    }

    override suspend fun signOut() {
        userPreferenceRepository.clearUserPreference()
    }
}
