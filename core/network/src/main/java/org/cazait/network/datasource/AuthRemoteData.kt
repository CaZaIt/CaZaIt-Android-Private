package org.cazait.network.datasource

import android.util.Log
import org.cazait.network.NetworkConnectivity
import org.cazait.network.api.auth.TokenService
import org.cazait.network.api.unauth.AuthService
import org.cazait.network.di.Authenticated
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.VerificationCodeReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.request.VerifyCodeReq
import org.cazait.network.model.dto.response.VerficationCodeRes
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes
import org.cazait.network.model.dto.response.VerifyCodeRes
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AuthRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val authService: AuthService,
    @Authenticated private val tokenService: TokenService
) : AuthRemoteDataSource {
    override suspend fun getRefreshToken(
        role: String,
        refreshToken: String
    ): DataResponse<RefreshTokenRes> {
        return when (val response = processCall {
            tokenService.getRefreshTokenAuth(role, refreshToken)
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

    override suspend fun postSignUpCode(body: VerificationCodeReq): DataResponse<VerficationCodeRes> {
        return when (val response = processCall {
            authService.postSignUpCode(body)
        }) {
            is VerficationCodeRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    override suspend fun postVerifyCode(body: VerifyCodeReq): DataResponse<VerifyCodeRes> {
        return when (val response = processCall {
            authService.postVerifyCode(body)
        }) {
            is VerifyCodeRes -> {
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
