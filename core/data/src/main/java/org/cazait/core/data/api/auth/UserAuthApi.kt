package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.request.CheckPasswordRequest
import org.cazait.core.data.datasource.response.ChangeNicknameResponse
import org.cazait.core.data.datasource.response.ChangePasswordResponse
import org.cazait.core.data.datasource.response.CheckPasswordResponse
import org.cazait.core.data.model.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAuthApi {
    @POST("/api/users/verify-password/{userId}")
    suspend fun postCheckPasswordAuth(
        @Path("userId") userId: String,
        @Body checkPassword: CheckPasswordRequest,
    ): NetworkResult<CheckPasswordResponse>

    @PATCH("/api/users/userinfo/password/{userId}")
    suspend fun patchPasswordAuth(
        @Path("userId") userId: String,
        @Body changePassword: org.cazait.core.data.datasource.request.ChangePasswordRequest,
    ): NetworkResult<ChangePasswordResponse>

    @PATCH("/api/users/userinfo/nickname/{userId}")
    suspend fun patchNicknameAuth(
        @Path("userId") userId: String,
        @Body changeNickname: org.cazait.core.data.datasource.request.ChangeNicknameRequest,
    ): NetworkResult<ChangeNicknameResponse>
}
