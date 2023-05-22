package org.cazait.data.source.remote.user

import org.cazait.data.dto.request.IsEmailDupReq
import org.cazait.data.dto.request.IsNicknameDupReq
import org.cazait.data.dto.request.SignUpReq
import org.cazait.data.dto.response.DataResponse
import org.cazait.data.dto.response.IsEmailDupRes
import org.cazait.data.dto.response.IsNicknameDupRes
import org.cazait.data.dto.response.SignUpRes

interface UserRemoteDataSource {
    suspend fun postSignUp(body: SignUpReq): DataResponse<SignUpRes>
    suspend fun postIsNicknameDup(body: IsNicknameDupReq): DataResponse<IsNicknameDupRes>
    suspend fun postIsEmailDup(body: IsEmailDupReq): DataResponse<IsEmailDupRes>
}
