package org.cazait.network.api.unauth

import org.cazait.network.model.dto.request.MessageReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.request.VerifyCodeReq
import org.cazait.network.model.dto.response.MessageRes
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes
import org.cazait.network.model.dto.response.VerifyCodeRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    @POST("/api/auths/log-in")
    suspend fun postSignIn(
        @Query("role")
        role: String = "user",
        @Body
        signInReq: SignInReq,
    ): Response<SignInRes>

    @POST("/api/auths/messages/codes/send/test")
    suspend fun postMessage(
        @Body messageReq: MessageReq
    ): Response<MessageRes>

    @POST("/api/auths/messages/codes/verify")
    suspend fun postVerifyCode(
        @Body verifyCode: VerifyCodeReq
    ): Response<VerifyCodeRes>
}
