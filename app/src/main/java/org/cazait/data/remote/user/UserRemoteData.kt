package org.cazait.data.remote.user

import org.cazait.data.Resource
import org.cazait.data.error.ErrorManager
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.IsNicknameDupRes
import org.cazait.data.model.response.SignUpRes
import org.cazait.data.remote.ServiceGenerator
import org.cazait.network.NetworkConnectivity
import javax.inject.Inject

class UserRemoteData @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity,
    private val errorManager: ErrorManager,
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
