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
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.AccountName
import org.cazait.core.domain.model.user.Password
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
        phoneNumber: String,
        nickname: String,
    ): NetworkResult<SignUpInfo> {
        val request = SignUpRequest(
            accountName = accountName.value,
            password = password.value,
            phoneNumber = phoneNumber,
            nickname = nickname,
        )
        return userRemoteDataSource.postSignUp(signUpRequest = request)
            .map(SignUpResponse::toData)
    }

    override suspend fun postCheckPhoneNumberExistence(
        phoneNumber: String,
        isExist: String,
    ): NetworkResult<ExistenceStatus> {
        val request = CheckPhoneNumberExistenceRequest(phoneNumber, isExist)
        return userRemoteDataSource.postCheckPhoneNumberExistence(
            checkPhoneNumRequest = request,
        ).map(ExistenceResponse::toData)
    }

    override suspend fun postCheckAccountNameExistence(
        accountName: String,
        isExist: String,
    ): NetworkResult<ExistenceStatus> {
        val checkUserIdRequest = CheckUserIdRequest(accountName, isExist)
        return userRemoteDataSource.postCheckAccountNameExistence(
            checkUserIdRequest = checkUserIdRequest,
        ).map(ExistenceResponse::toData)
    }

    override suspend fun postCheckNicknameExistence(
        nickname: String,
        isExist: String,
    ): NetworkResult<ExistenceStatus> {
        val checkNicknameRequest = CheckNicknameExistenceRequest(nickname, isExist)
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
        userId: String,
        password: String,
    ): NetworkResult<String> {
        val checkPasswordRequest = CheckPasswordRequest(password)
        return userRemoteDataSource.postCheckPassword(
            userUuid = userId,
            checkPasswordRequest = checkPasswordRequest,
        ).map { it.message }
    }

    override suspend fun changePassword(
        userId: String,
        password: String,
    ): NetworkResult<String> {
        val changePasswordRequest = ChangePasswordRequest(password)
        return userRemoteDataSource.patchChangePassword(
            userUuid = userId,
            changePasswordRequest = changePasswordRequest,
        ).map { it.message }
    }

    override suspend fun changeNickname(
        userId: String,
        nickname: String,
    ): NetworkResult<String> {
        val changeNicknameRequest = ChangeNicknameRequest(nickname)
        return userRemoteDataSource.patchChangeNickname(
            userUuid = userId,
            changeNicknameRequest = changeNicknameRequest,
        ).map { it.message }
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        val userPreference = userPreferenceRepository.getUserPreference().first()
        Log.e("UserRepository", "id = ${userPreference.uuid}")
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
