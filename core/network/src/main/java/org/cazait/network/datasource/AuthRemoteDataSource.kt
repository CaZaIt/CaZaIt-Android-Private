package org.cazait.network.datasource

import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes

interface AuthRemoteDataSource {
    suspend fun postSignIn(body: SignInReq): DataResponse<SignInRes>
    suspend fun getRefreshToken(
        userId: String,
        role: String,
        jwtToken: String,
        refreshToken: String
    ): DataResponse<RefreshTokenRes>
}
