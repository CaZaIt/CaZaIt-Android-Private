package org.cazait.core.data.api.auth

import org.cazait.core.data.datasource.request.ChangeNicknameRequest
import org.cazait.core.data.datasource.request.ChangePasswordRequest
import org.cazait.core.data.datasource.request.CheckPasswordRequest
import org.cazait.core.data.datasource.response.ChangeNicknameResponse
import org.cazait.core.data.datasource.response.ChangePasswordResponse
import org.cazait.core.data.datasource.response.CheckPasswordResponse
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAuthApi {
    @POST("/api/users/verify-password/{userId}")
    suspend fun postCheckPasswordAuth(
        @Path("userId") userId: String,
        @Body checkPasswordRequest: CheckPasswordRequest,
    ): NetworkResult<CheckPasswordResponse>

    @PATCH("/api/users/userinfo/password/{userId}")
    suspend fun patchPasswordAuth(
        @Path("userId") userId: String,
        @Body changePasswordRequest: ChangePasswordRequest,
    ): NetworkResult<ChangePasswordResponse>

    @PATCH("/api/users/userinfo/nickname/{userId}")
    suspend fun patchNicknameAuth(
        @Path("userId") userId: String,
        @Body changeNicknameRequest: ChangeNicknameRequest,
    ): NetworkResult<ChangeNicknameResponse>
}
