package org.cazait.data.remote.user

import android.util.Log
import org.cazait.data.Resource
import org.cazait.data.api.UserService
import org.cazait.data.error.ErrorManager
import org.cazait.data.error.NO_INTERNET_CONNECTION
import org.cazait.data.model.request.IsEmailDupReq
import org.cazait.data.model.request.IsNicknameDupReq
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.IsNicknameDupRes
import org.cazait.data.model.response.SignUpRes
import org.cazait.network.NetworkConnectivity
import java.io.IOException
import javax.inject.Inject

class UserRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val errorManager: ErrorManager,
    private val userService: UserService
) : UserRemoteDataSource {
    override fun postIsEmailDup(body: IsEmailDupReq): Resource<IsEmailDupRes> {
        if (!networkConnectivity.isConnected()) {
            return Resource.Error(errorManager.getError(NO_INTERNET_CONNECTION).description)
        }
        return try {
            val response = userService.postIsEmailDup(body).execute()
            if (response.isSuccessful)
                Resource.Success(response.body()!!)
            else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }

    override fun postIsNicknameDup(body: IsNicknameDupReq): Resource<IsNicknameDupRes> {
        if (!networkConnectivity.isConnected()) {
            return Resource.Error(errorManager.getError(NO_INTERNET_CONNECTION).description)
        }
        return try {
            val response = userService.postIsNicknameDup(body).execute()
            if (response.isSuccessful)
                Resource.Success(response.body()!!)
            else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }

    override fun postSignUp(body: SignUpReq): Resource<SignUpRes> {
        if (!networkConnectivity.isConnected()) {
            return Resource.Error(errorManager.getError(NO_INTERNET_CONNECTION).description)
        }

        return try {
            val response = userService.postSignUp(body).execute()
            if (response.isSuccessful)
                Resource.Success(response.body()!!)
            else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}
