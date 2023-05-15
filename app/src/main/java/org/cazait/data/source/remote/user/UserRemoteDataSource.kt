package org.cazait.data.source.remote.user

import org.cazait.data.Resource
import org.cazait.data.dto.request.IsEmailDupReq
import org.cazait.data.dto.request.IsNicknameDupReq
import org.cazait.data.dto.request.SignUpReq
import org.cazait.data.dto.response.IsEmailDupRes
import org.cazait.data.dto.response.IsNicknameDupRes
import org.cazait.data.dto.response.SignUpRes

interface UserRemoteDataSource {
    fun postSignUp(body: SignUpReq): Resource<SignUpRes>
    fun postIsNicknameDup(body: IsNicknameDupReq): Resource<IsNicknameDupRes>
    fun postIsEmailDup(body: IsEmailDupReq): Resource<IsEmailDupRes>
}
