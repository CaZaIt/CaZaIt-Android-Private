package org.cazait.core.data.api.unauth

import org.cazait.core.data.datasource.request.CheckNicknameExistenceRequest
import org.cazait.core.data.datasource.request.CheckPhoneNumberExistenceRequest
import org.cazait.core.data.datasource.request.CheckUserDataRequest
import org.cazait.core.data.datasource.request.CheckUserIdRequest
import org.cazait.core.data.datasource.request.FindUserIdRequest
import org.cazait.core.data.datasource.request.ResetPasswordRequest
import org.cazait.core.data.datasource.request.SignUpRequest
import org.cazait.core.data.datasource.response.ExistenceResponse
import org.cazait.core.data.datasource.response.CheckUserDataResponse
import org.cazait.core.data.datasource.response.FindUserIdResponse
import org.cazait.core.data.datasource.response.ResetPasswordResponse
import org.cazait.core.data.datasource.response.SignUpResponse
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/api/users/sign-up")
    suspend fun postSignUp(
        @Body signUpRequest: SignUpRequest,
    ): NetworkResult<SignUpResponse>

    @POST("/api/users/exist/phonenumber")
    suspend fun postPhoneDB(
        @Body checkPhoneNumberRequest: CheckPhoneNumberExistenceRequest,
    ): NetworkResult<ExistenceResponse>

    @POST("/api/users/exist/nickname")
    suspend fun postNicknameDB(
        @Body checkNicknameExistenceRequest: CheckNicknameExistenceRequest,
    ): NetworkResult<ExistenceResponse>

    @POST("/api/users/exist/accountname")
    suspend fun postUserIdDB(
        @Body checkUserIdRequest: CheckUserIdRequest,
    ): NetworkResult<ExistenceResponse>

    @POST("/api/users/find-accountname")
    suspend fun postFindUserId(
        @Body findUserIdRequest: FindUserIdRequest,
    ): NetworkResult<FindUserIdResponse>

    @POST("/api/users/reset-password/checkuserinfo/{userId}")
    suspend fun postCheckUserData(
        @Path("userId") userId: String,
        @Body checkUserDataRequest: CheckUserDataRequest,
    ): NetworkResult<CheckUserDataResponse>

    @PATCH("/api/users/reset-password/password/{userId}")
    suspend fun patchPassword(
        @Path("userId") userId: String,
        @Body resetPasswordRequest: ResetPasswordRequest,
    ): NetworkResult<ResetPasswordResponse>
}
