package org.cazait.network.api.unauth

import org.cazait.network.model.dto.request.CheckNicknameReq
import org.cazait.network.model.dto.request.CheckPhoneNumReq
import org.cazait.network.model.dto.request.CheckUserIdReq
import org.cazait.network.model.dto.request.FindUserIdReq
import org.cazait.network.model.dto.request.ResetPasswordReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.CheckRes
import org.cazait.network.model.dto.response.FindUserIdRes
import org.cazait.network.model.dto.response.ResetPasswordRes
import org.cazait.network.model.dto.response.SignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserService {
    @POST("/api/users/sign-up")
    suspend fun postSignUp(@Body signUpRequest: SignUpReq): Response<SignUpRes>

    @POST("/api/users/exist/phonenumber")
    suspend fun postPhoneDB(@Body checkPhoneNum: CheckPhoneNumReq): Response<CheckRes>

    @POST("/api/users/exist/nickname")
    suspend fun postNicknameDB(@Body checkNickname: CheckNicknameReq): Response<CheckRes>

    @POST("/api/users/exist/accountname")
    suspend fun postUserIdDB(@Body checkUserId: CheckUserIdReq): Response<CheckRes>

    @POST("/api/users/find-accountname")
    suspend fun postFindUserId(@Body userPhoneNumber: FindUserIdReq): Response<FindUserIdRes>
    @PATCH("/api/users/reset-password/password")
    suspend fun patchPassword(@Body resetPassword: ResetPasswordReq): Response<ResetPasswordRes>
}
