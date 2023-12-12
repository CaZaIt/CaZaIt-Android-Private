package org.cazait.core.data.datasource

import org.cazait.core.data.api.auth.UserAuthApi
import org.cazait.core.data.api.unauth.UserApi
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
import org.cazait.core.data.datasource.response.ChangeNicknameResponse
import org.cazait.core.data.datasource.response.ChangePasswordResponse
import org.cazait.core.data.datasource.response.CheckPasswordResponse
import org.cazait.core.data.datasource.response.ExistenceResponse
import org.cazait.core.data.datasource.response.CheckUserDataResponse
import org.cazait.core.data.datasource.response.FindUserIdResponse
import org.cazait.core.data.datasource.response.ResetPasswordResponse
import org.cazait.core.data.datasource.response.SignUpResponse
import org.cazait.core.data.di.Authenticated
import org.cazait.core.domain.model.network.NetworkResult
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi,
    @Authenticated private val userAuthApi: UserAuthApi,
) {
    suspend fun postSignUp(
        signUpRequest: SignUpRequest,
    ): NetworkResult<SignUpResponse> = userApi.postSignUp(
        signUpRequest = signUpRequest,
    )

    suspend fun postCheckPhoneNumberExistence(
        checkPhoneNumRequest: CheckPhoneNumberExistenceRequest,
    ): NetworkResult<ExistenceResponse> = userApi.postPhoneDB(
        checkPhoneNumberRequest = checkPhoneNumRequest,
    )

    suspend fun postCheckAccountNameExistence(
        checkUserIdRequest: CheckUserIdRequest,
    ): NetworkResult<ExistenceResponse> = userApi.postUserIdDB(
        checkUserIdRequest = checkUserIdRequest,
    )

    suspend fun postCheckNicknameExistence(
        checkNicknameExistenceRequest: CheckNicknameExistenceRequest,
    ): NetworkResult<ExistenceResponse> = userApi.postNicknameDB(
        checkNicknameExistenceRequest = checkNicknameExistenceRequest,
    )

    suspend fun postCheckUserData(
        userUuid: String,
        checkUserDataRequest: CheckUserDataRequest,
    ): NetworkResult<CheckUserDataResponse> = userApi.postCheckUserData(
        userId = userUuid,
        checkUserDataRequest = checkUserDataRequest,
    )

    suspend fun postFindUserId(
        findUserIdRequest: FindUserIdRequest,
    ): NetworkResult<FindUserIdResponse> = userApi.postFindUserId(
        findUserIdRequest = findUserIdRequest,
    )

    suspend fun patchPassword(
        userUuid: String,
        resetPasswordRequest: ResetPasswordRequest,
    ): NetworkResult<ResetPasswordResponse> = userApi.patchPassword(
        userId = userUuid,
        resetPasswordRequest = resetPasswordRequest,
    )

    suspend fun postCheckPassword(
        userUuid: String,
        checkPasswordRequest: CheckPasswordRequest,
    ): NetworkResult<CheckPasswordResponse> = userAuthApi.postCheckPasswordAuth(
        userId = userUuid,
        checkPasswordRequest = checkPasswordRequest,
    )

    suspend fun patchChangePassword(
        userUuid: String,
        changePasswordRequest: ChangePasswordRequest,
    ): NetworkResult<ChangePasswordResponse> = userAuthApi.patchPasswordAuth(
        userId = userUuid,
        changePasswordRequest = changePasswordRequest,
    )

    suspend fun patchChangeNickname(
        userUuid: String,
        changeNicknameRequest: ChangeNicknameRequest,
    ): NetworkResult<ChangeNicknameResponse> = userAuthApi.patchNicknameAuth(
        userId = userUuid,
        changeNicknameRequest = changeNicknameRequest,
    )
}
