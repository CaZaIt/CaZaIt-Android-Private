package org.cazait.network.api.auth

import org.cazait.network.model.dto.request.ChangeNicknameReq
import org.cazait.network.model.dto.request.ChangePasswordReq
import org.cazait.network.model.dto.request.CheckPasswordReq
import org.cazait.network.model.dto.response.ChangePasswordRes
import org.cazait.network.model.dto.response.CheckPasswordRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAuthService {
    @POST("/api/users/verify-password/{userId}")
    suspend fun postCheckPasswordAuth(
        @Path("userId") userId: String,
        @Body checkPassword: CheckPasswordReq
    ): Response<CheckPasswordRes>

    @PATCH("/api/users/userinfo/password/{userId}")
    suspend fun patchPasswordAuth(
        @Path("userId") userId: String,
        @Body changePassword: ChangePasswordReq
    ): Response<ChangePasswordRes>

    @PATCH("/api/users/userinfo/nickname/{userId}")
    suspend fun patchNicknameAuth(
        @Path("userId") userId: String,
        @Body changeNickname: ChangeNicknameReq
    ): Response<ChangePasswordRes>
}