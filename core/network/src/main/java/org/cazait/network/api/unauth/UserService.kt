package org.cazait.network.api.unauth

import org.cazait.network.model.dto.request.IsIdNumberDupReq
import org.cazait.network.model.dto.request.IsNicknameDupReq
import org.cazait.network.model.dto.request.SignUpReq
import org.cazait.network.model.dto.response.IsIdNumberDupRes
import org.cazait.network.model.dto.response.IsNicknameDupRes
import org.cazait.network.model.dto.response.SignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/users/sign-up")
    suspend fun postSignUp(@Body signUpRequest: SignUpReq): Response<SignUpRes>

    @POST("/api/users/accountnumber")
    suspend fun postIsIdNumberDup(@Body accountNumber: IsIdNumberDupReq): Response<IsIdNumberDupRes>

    @POST("/api/users/nickname")
    suspend fun postIsNicknameDup(@Body nickname: IsNicknameDupReq): Response<IsNicknameDupRes>
}
