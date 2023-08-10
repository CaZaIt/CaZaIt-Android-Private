package org.cazait.network.datasource

import org.cazait.network.model.dto.request.IsUserIdDupReq
import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.IsUserIdDupRes
import org.cazait.network.model.dto.response.IsNicknameDupRes
import org.cazait.network.model.dto.response.SignUpRes
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.FindUserIdReq
import org.cazait.network.model.dto.request.ResetPasswordReq
import org.cazait.network.model.dto.response.FindUserIdRes
import org.cazait.network.model.dto.response.ResetPasswordRes

interface UserRemoteDataSource {
    suspend fun postSignUp(body: SignUpReq): DataResponse<SignUpRes>
    suspend fun postIsUserIdDup(body: IsUserIdDupReq): DataResponse<IsUserIdDupRes>
    suspend fun postIsNicknameDup(body: IsNicknameDupReq): DataResponse<IsNicknameDupRes>
    suspend fun postFindUserId(body: FindUserIdReq): DataResponse<FindUserIdRes>
    suspend fun patchPassword(body: ResetPasswordReq): DataResponse<ResetPasswordRes>
}
