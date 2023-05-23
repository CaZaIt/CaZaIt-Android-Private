package org.cazait.network.datasource

import org.cazait.network.model.dto.request.IsEmailDupReq
import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.IsEmailDupRes
import org.cazait.network.model.dto.response.IsNicknameDupRes
import org.cazait.network.model.dto.response.SignUpRes
import org.cazait.network.model.dto.DataResponse

interface UserRemoteDataSource {
    suspend fun postSignUp(body: SignUpReq): DataResponse<SignUpRes>
    suspend fun postIsNicknameDup(body: IsNicknameDupReq): DataResponse<IsNicknameDupRes>
    suspend fun postIsEmailDup(body: IsEmailDupReq): DataResponse<IsEmailDupRes>
}
