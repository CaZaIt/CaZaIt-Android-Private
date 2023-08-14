package org.cazait.network.api.unauth

import org.cazait.network.model.dto.request.VerificationCodeReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.request.VerifyCodeReq
import org.cazait.network.model.dto.response.VerficationCodeRes
import org.cazait.network.model.dto.response.SignInRes
import org.cazait.network.model.dto.response.VerifyCodeRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("/api/auths/log-in")
    suspend fun postSignIn(
        @Query("role")
        role: String = "user",
        @Body
        signInReq: SignInReq,
    ): Response<SignInRes>

    @POST("/api/auths/messages/codes/verify")
    suspend fun postVerifyCode(
        @Body verifyCode: VerifyCodeReq
    ): Response<VerifyCodeRes>

    @POST("/api/auths/messages/codes/send/sign-up")
    suspend fun postSignUpCode(
        @Body verificationCode: VerificationCodeReq
    ): Response<VerficationCodeRes>
    
    @POST("/api/auths/messages/codes/send/reset-password")
    suspend fun postResetPasswordCode(

    )

    @POST("/api/auths/messages/codes/send/find_accountname")
    suspend fun postFindIdCode(
        @Body verificationCode: VerificationCodeReq
    ): Response<VerficationCodeRes>
}
