package org.cazait.network.datasource

import android.util.Log
import org.cazait.network.NetworkConnectivity
import org.cazait.network.api.AuthService
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AuthRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun getRefreshToken(userId: Long, role: String, accessToken: String, refreshToken: String): DataResponse<RefreshTokenRes> {
        return when(val response = processCall {
            authService.getRefreshToken(userId, role, accessToken, refreshToken)
        }) {
            is RefreshTokenRes -> {
                DataResponse.Success(response)
            }
            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    override suspend fun postSignIn(body: SignInReq): DataResponse<SignInRes> {
        Log.e("AuthRemoteData", body.toString())
        return when (val response = processCall {
            authService.postSignIn(role = "user", signInReq = body)
        }) {
            is SignInRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
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
