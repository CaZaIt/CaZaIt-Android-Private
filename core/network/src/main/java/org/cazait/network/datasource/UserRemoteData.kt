package org.cazait.network.datasource

import org.cazait.network.model.dto.request.IsEmailDupReq
import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.response.IsEmailDupRes
import org.cazait.network.model.dto.response.IsNicknameDupRes
import org.cazait.network.model.dto.response.SignUpRes
import org.cazait.network.NetworkConnectivity
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.error.NO_INTERNET_CONNECTION
import org.cazait.network.api.UserService
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UserRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val userService: UserService
) : UserRemoteDataSource {
    override suspend fun postIsEmailDup(
        body: IsEmailDupReq
    ): DataResponse<IsEmailDupRes> {
        return when (val response = processCall {
            userService.postIsEmailDup(body.email)
        }) {
            is IsEmailDupRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postIsNicknameDup(body: IsNicknameDupReq): DataResponse<IsNicknameDupRes> {
        return when (val response = processCall {
            userService.postIsNicknameDup(body)
        }) {
            is IsNicknameDupRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

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
