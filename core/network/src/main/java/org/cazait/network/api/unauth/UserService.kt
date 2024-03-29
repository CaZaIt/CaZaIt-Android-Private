package org.cazait.network.api.unauth

import org.cazait.network.model.dto.request.CheckNicknameReq
import org.cazait.network.model.dto.request.CheckPhoneNumReq
import org.cazait.network.model.dto.request.CheckUserDataReq
import org.cazait.network.model.dto.request.CheckUserIdReq
import org.cazait.network.model.dto.request.FindUserIdReq
import org.cazait.network.model.dto.request.ResetPasswordReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.CheckRes
import org.cazait.network.model.dto.response.CheckUserDataRes
import org.cazait.network.model.dto.response.FindUserIdRes
import org.cazait.network.model.dto.response.ResetPasswordRes
import org.cazait.network.model.dto.response.SignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("/api/users/reset-password/checkuserinfo/{userId}")
    suspend fun postCheckUserData(
        @Path("userId") userUuid: String,
        @Body userPhoneNumber: CheckUserDataReq
    ): Response<CheckUserDataRes>

    @PATCH("/api/users/reset-password/password/{userId}")
    suspend fun patchPassword(
        @Path("userId") userUuid: String,
        @Body resetPassword: ResetPasswordReq
    ): Response<ResetPasswordRes>
}
