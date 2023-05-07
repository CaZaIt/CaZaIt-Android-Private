package org.cazait.data.remote.auth

import org.cazait.data.Resource
import org.cazait.data.dto.request.RefreshTokenReq
import org.cazait.data.model.request.SignInReq
import org.cazait.data.dto.response.RefreshTokenRes
import org.cazait.data.model.response.SignInRes

interface AuthRemoteDataSource {
    fun postSignIn(body: SignInReq): Resource<SignInRes>
    fun getRefreshToken(body: RefreshTokenReq): Resource<RefreshTokenRes>
}
