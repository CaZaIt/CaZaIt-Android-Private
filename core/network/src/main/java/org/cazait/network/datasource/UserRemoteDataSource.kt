package org.cazait.network.datasource

import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.SignUpRes
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.CheckNicknameReq
import org.cazait.network.model.dto.request.CheckPhoneNumReq
import org.cazait.network.model.dto.request.CheckUserDataReq
import org.cazait.network.model.dto.request.CheckUserIdReq
import org.cazait.network.model.dto.request.FindUserIdReq
import org.cazait.network.model.dto.request.ResetPasswordReq
import org.cazait.network.model.dto.response.CheckRes
import org.cazait.network.model.dto.response.CheckUserDataRes
import org.cazait.network.model.dto.response.FindUserIdRes
import org.cazait.network.model.dto.response.ResetPasswordRes

interface UserRemoteDataSource {
    suspend fun postSignUp(body: SignUpReq): DataResponse<SignUpRes>
    suspend fun postCheckPhoneNum(body: CheckPhoneNumReq): DataResponse<CheckRes>
    suspend fun postCheckUserId(body: CheckUserIdReq): DataResponse<CheckRes>
    suspend fun postCheckNickname(body: CheckNicknameReq): DataResponse<CheckRes>
    suspend fun postCheckUserData(
        userUuid: String,
        body: CheckUserDataReq
    ): DataResponse<CheckUserDataRes>

    suspend fun postFindUserId(body: FindUserIdReq): DataResponse<FindUserIdRes>
    suspend fun patchPassword(
        userUuid: String,
        body: ResetPasswordReq
    ): DataResponse<ResetPasswordRes>
}
