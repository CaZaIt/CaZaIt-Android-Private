package org.cazait.data.source.remote.auth

import org.cazait.domain.model.Resource
import org.cazait.data.dto.request.RefreshTokenReq
import org.cazait.data.dto.request.SignInReq
import org.cazait.data.dto.response.RefreshTokenRes
import org.cazait.data.dto.response.SignInRes

interface AuthRemoteDataSource {
    fun postSignIn(body: SignInReq): Resource<SignInRes>
    fun getRefreshToken(body: RefreshTokenReq): Resource<RefreshTokenRes>
}
