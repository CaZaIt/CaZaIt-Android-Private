package org.cazait.data.remote.user

import org.cazait.data.Resource
import org.cazait.data.api.UserService
import org.cazait.data.error.ErrorManager
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.IsNicknameDupRes
import org.cazait.data.model.response.SignUpRes
import org.cazait.network.NetworkConnectivity
import javax.inject.Inject

class UserRemoteData @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val errorManager: ErrorManager,
    private val userService: UserService
) : UserRemoteDataSource {
    override fun getIsEmailDup(): Resource<IsEmailDupRes> {
        TODO()
    }

    override fun getIsNicknameDup(): Resource<IsNicknameDupRes> {
        TODO()
    }

    override fun postSignUp(body: SignUpReq): Resource<SignUpRes> {
        TODO()
    }
}
