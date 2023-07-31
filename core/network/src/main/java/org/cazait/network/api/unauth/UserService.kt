package org.cazait.network.api.unauth

import org.cazait.network.model.dto.request.IsUserIdDupReq
import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.IsUserIdDupRes
import org.cazait.network.model.dto.response.IsNicknameDupRes
import org.cazait.network.model.dto.response.SignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/users/sign-up")
    suspend fun postSignUp(@Body signUpRequest: SignUpReq): Response<SignUpRes>

    @POST("/api/users/duplicate-check/accountname")
    suspend fun postIsUserIdDup(@Body accountNumber: IsUserIdDupReq): Response<IsUserIdDupRes>

    @POST("/api/users/duplicate-check/nickname")
    suspend fun postIsNicknameDup(@Body nickname: IsNicknameDupReq): Response<IsNicknameDupRes>
}
