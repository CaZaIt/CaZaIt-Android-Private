package org.cazait.data.remote.user

import org.cazait.data.Resource
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.IsNicknameDupRes
import org.cazait.data.model.response.SignUpRes

interface UserRemoteDataSource {
    fun postSignUp(body: SignUpReq): Resource<SignUpRes>
    fun getIsNicknameDup(): Resource<IsNicknameDupRes>
    fun getIsEmailDup(): Resource<IsEmailDupRes>
}
