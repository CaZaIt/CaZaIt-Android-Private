package org.cazait.network.api

import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.IsEmailDupRes
import org.cazait.network.model.dto.response.IsNicknameDupRes
import org.cazait.network.model.dto.response.SignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/users/sign-up")
    suspend fun postSignUp(@Body signUpRequest: SignUpReq): Response<SignUpRes>

    @POST("/api/users/email")
    suspend fun postIsEmailDup(@Body email: String): Response<IsEmailDupRes>

    @POST("/api/users/nickname")
    suspend fun postIsNicknameDup(@Body nickname: IsNicknameDupReq): Response<IsNicknameDupRes>
}