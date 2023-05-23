package org.cazait.network.datasource

import org.cazait.network.model.dto.request.RefreshTokenReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes
import org.cazait.network.NetworkConnectivity
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.error.NO_INTERNET_CONNECTION
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.api.AuthService
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AuthRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun getRefreshToken(body: RefreshTokenReq): DataResponse<RefreshTokenRes> {
        TODO("NOT IMPLEMENTED YET")
    }

    override suspend fun postSignIn(body: SignInReq): DataResponse<SignInRes> {
        return when (val response = processCall {
            authService.postSignIn(signInReq = body)
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
        if (!networkConnectivity.isConnected()) {
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
