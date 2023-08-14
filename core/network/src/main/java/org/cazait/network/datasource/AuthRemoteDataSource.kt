package org.cazait.network.datasource

import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.MessageReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.request.VerifyCodeReq
import org.cazait.network.model.dto.response.MessageRes
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes
import org.cazait.network.model.dto.response.VerifyCodeRes

interface AuthRemoteDataSource {
    suspend fun postSignIn(body: SignInReq): DataResponse<SignInRes>
    suspend fun getRefreshToken(role: String, refreshToken: String): DataResponse<RefreshTokenRes>
    suspend fun postSignUpCode(body: MessageReq): DataResponse<MessageRes>
    suspend fun postVerifyCode(body: VerifyCodeReq): DataResponse<VerifyCodeRes>
}
