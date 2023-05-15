package org.cazait.data.source.remote.auth

import org.cazait.data.Resource
import org.cazait.data.api.AuthService
import org.cazait.data.error.ErrorManager
import org.cazait.data.error.NO_INTERNET_CONNECTION
import org.cazait.data.dto.request.RefreshTokenReq
import org.cazait.data.dto.request.SignInReq
import org.cazait.data.dto.response.RefreshTokenRes
import org.cazait.data.dto.response.SignInRes
import org.cazait.network.NetworkConnectivity
import java.io.IOException
import javax.inject.Inject

class AuthRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val errorManager: ErrorManager,
    private val authService: AuthService
) : AuthRemoteDataSource {
    override fun getRefreshToken(body: RefreshTokenReq): Resource<RefreshTokenRes> {
        TODO("NOT IMPLEMENTED YET")
    }

    override fun postSignIn(body: SignInReq): Resource<SignInRes> {
        if (!networkConnectivity.isConnected()) {
            return Resource.Error(errorManager.getError(NO_INTERNET_CONNECTION).description)
        }

        return try {
            val response = authService.postSignIn(signInReq = body).execute()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}
