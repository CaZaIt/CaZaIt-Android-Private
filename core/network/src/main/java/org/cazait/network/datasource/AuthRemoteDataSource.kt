package org.cazait.network.datasource

import org.cazait.network.model.dto.request.RefreshTokenReq
import org.cazait.network.model.dto.request.SignInReq
import org.cazait.network.model.dto.response.RefreshTokenRes
import org.cazait.network.model.dto.response.SignInRes
import org.cazait.network.model.dto.DataResponse

interface AuthRemoteDataSource {
    suspend fun postSignIn(body: SignInReq): DataResponse<SignInRes>
    suspend fun getRefreshToken(body: RefreshTokenReq): DataResponse<RefreshTokenRes>
}
