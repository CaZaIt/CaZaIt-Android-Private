package org.cazait.core.data.api.unauth

import org.cazait.core.data.datasource.request.CheckNicknameRequest
import org.cazait.core.data.datasource.request.CheckPhoneNumRequest
import org.cazait.core.data.datasource.request.CheckUserIdRequest
import org.cazait.core.data.datasource.request.SignUpRequest
import org.cazait.core.data.datasource.response.CheckResponse
import org.cazait.core.data.datasource.response.SignUpResponse
import org.cazait.core.data.model.network.NetworkResult
import org.cazait.core.data.model.response.CheckResponse
import org.cazait.core.data.model.response.CheckUserDataRes
import org.cazait.core.data.model.response.FindUserIdRes
import org.cazait.core.data.model.response.ResetPasswordRes
import org.cazait.core.data.model.response.SignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/api/users/sign-up")
    suspend fun postSignUp(
        @Body signUpRequest: SignUpRequest
    ): NetworkResult<SignUpResponse>

    @POST("/api/users/exist/phonenumber")
    suspend fun postPhoneDB(
        @Body checkPhoneNum: CheckPhoneNumRequest
    ): NetworkResult<CheckResponse>

    @POST("/api/users/exist/nickname")
    suspend fun postNicknameDB(
        @Body checkNickname: CheckNicknameRequest
    ): NetworkResult<CheckResponse>

    @POST("/api/users/exist/accountname")
    suspend fun postUserIdDB(
        @Body checkUserId: CheckUserIdRequest
    ): NetworkResult<CheckResponse>

    @POST("/api/users/find-accountname")
    suspend fun postFindUserId(@Body userPhoneNumber: org.cazait.core.data.datasource.request.FindUserIdRequest): Response<FindUserIdRes>

    @POST("/api/users/reset-password/checkuserinfo/{userId}")
    suspend fun postCheckUserData(
        @Path("userId") userUuid: String,
        @Body userPhoneNumber: org.cazait.core.data.datasource.request.CheckUserDataRequest,
    ): Response<CheckUserDataRes>

    @PATCH("/api/users/reset-password/password/{userId}")
    suspend fun patchPassword(
        @Path("userId") userUuid: String,
        @Body resetPassword: org.cazait.core.data.datasource.request.ResetPasswordRequest,
    ): Response<ResetPasswordRes>
}
