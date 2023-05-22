package org.cazait.data.source.remote.user

import android.util.Log
import org.cazait.data.api.UserService
import org.cazait.data.dto.request.IsEmailDupReq
import org.cazait.data.dto.request.IsNicknameDupReq
import org.cazait.data.dto.request.SignUpReq
import org.cazait.data.dto.response.DataResponse
import org.cazait.data.dto.response.IsEmailDupRes
import org.cazait.data.dto.response.IsNicknameDupRes
import org.cazait.data.dto.response.SignUpRes
import org.cazait.data.error.NETWORK_ERROR
import org.cazait.data.error.NO_INTERNET_CONNECTION
import org.cazait.network.NetworkConnectivity
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
                Log.e("UserRemoteData", "여기부터 곱창")
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
        if (!networkConnectivity.isConnected()) {
            Log.e("UserRemoteData", "NO_INTERNET_CONNECTION")
            return NO_INTERNET_CONNECTION
        }
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
