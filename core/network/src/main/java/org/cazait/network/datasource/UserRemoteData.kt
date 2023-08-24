package org.cazait.network.datasource

import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.response.SignUpRes
import org.cazait.network.NetworkConnectivity
import org.cazait.network.api.auth.UserAuthService
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.api.unauth.UserService
import org.cazait.network.di.Authenticated
import org.cazait.network.model.dto.request.ChangeNicknameReq
import org.cazait.network.model.dto.request.ChangePasswordReq
import org.cazait.network.model.dto.request.CheckNicknameReq
import org.cazait.network.model.dto.request.CheckPasswordReq
import org.cazait.network.model.dto.request.CheckPhoneNumReq
import org.cazait.network.model.dto.request.CheckUserDataReq
import org.cazait.network.model.dto.request.CheckUserIdReq
import org.cazait.network.model.dto.request.FindUserIdReq
import org.cazait.network.model.dto.request.ResetPasswordReq
import org.cazait.network.model.dto.response.ChangeNicknameRes
import org.cazait.network.model.dto.response.ChangePasswordRes
import org.cazait.network.model.dto.response.CheckPasswordRes
import org.cazait.network.model.dto.response.CheckRes
import org.cazait.network.model.dto.response.CheckUserDataRes
import org.cazait.network.model.dto.response.FindUserIdRes
import org.cazait.network.model.dto.response.ResetPasswordRes
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UserRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val userService: UserService,
    @Authenticated private val userAuthService: UserAuthService
) : UserRemoteDataSource {
    override suspend fun postSignUp(body: SignUpReq): DataResponse<SignUpRes> {
        return when (val response = processCall {
            userService.postSignUp(body)
        }) {
            is SignUpRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postCheckPhoneNum(body: CheckPhoneNumReq): DataResponse<CheckRes> {
        return when (val response = processCall {
            userService.postPhoneDB(body)
        }) {
            is CheckRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postCheckUserId(body: CheckUserIdReq): DataResponse<CheckRes> {
        return when (val response = processCall {
            userService.postUserIdDB(body)
        }) {
            is CheckRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postCheckNickname(body: CheckNicknameReq): DataResponse<CheckRes> {
        return when (val response = processCall {
            userService.postNicknameDB(body)
        }) {
            is CheckRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postCheckUserData(
        userUuid: String,
        body: CheckUserDataReq
    ): DataResponse<CheckUserDataRes> {
        return when (val response = processCall { userService.postCheckUserData(userUuid, body) }) {
            is CheckUserDataRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postFindUserId(body: FindUserIdReq): DataResponse<FindUserIdRes> {
        return when (val response = processCall {
            userService.postFindUserId(body)
        }) {
            is FindUserIdRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun patchPassword(
        userUuid: String,
        body: ResetPasswordReq
    ): DataResponse<ResetPasswordRes> {
        return when (val response = processCall {
            userService.patchPassword(userUuid, body)
        }) {
            is ResetPasswordRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postCheckPassword(
        userUuid: String,
        body: CheckPasswordReq
    ): DataResponse<CheckPasswordRes> {
        return when (val response =
            processCall { userAuthService.postCheckPasswordAuth(userUuid, body) }) {
            is CheckPasswordRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun patchChangePassword(
        userUuid: String,
        body: ChangePasswordReq
    ): DataResponse<ChangePasswordRes> {
        return when (val response =
            processCall { userAuthService.patchPasswordAuth(userUuid, body) }) {
            is ChangePasswordRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun patchChangeNickname(
        userUuid: String,
        body: ChangeNicknameReq
    ): DataResponse<ChangeNicknameRes> {
        return when (val response =
            processCall { userAuthService.patchNicknameAuth(userUuid, body) }) {
            is ChangeNicknameRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(
        responseCall: suspend () -> Response<*>
    ): Any? {
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
