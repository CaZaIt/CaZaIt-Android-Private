package org.cazait.core.data.api.unauth

import org.cazait.core.data.datasource.request.SignInRequest
import org.cazait.core.data.datasource.request.VerificationCodeRequest
import org.cazait.core.data.datasource.request.VerifyCodeRequest
import org.cazait.core.data.datasource.response.SignInResponse
import org.cazait.core.data.datasource.response.VerificationCodeResponse
import org.cazait.core.data.datasource.response.VerifyCodeResponse
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/api/auths/log-in")
    suspend fun postSignIn(
        @Query("role")
        role: String = "user",
        @Body
        signInRequest: SignInRequest,
    ): NetworkResult<SignInResponse>

    @POST("/api/auths/verify-authnumber")
    suspend fun postVerifyCode(
        @Body verifyCodeRequest: VerifyCodeRequest,
    ): NetworkResult<VerifyCodeResponse>

    @POST("/api/auths/send-authnumber/test")
    suspend fun postVerificationCode(
        @Body verificationCodeRequest: VerificationCodeRequest,
    ): NetworkResult<VerificationCodeResponse>
}
