package org.cazait.data.api

import org.cazait.data.model.request.IsEmailDupReq
import org.cazait.data.model.request.IsNicknameDupReq
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.IsNicknameDupRes
import org.cazait.data.model.response.SignUpRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/users/sign-up")
    fun postSignUp(@Body signUpRequest: SignUpReq): Call<SignUpRes>

    @POST("/api/users/email")
    fun postIsEmailDup(@Body email: IsEmailDupReq): Call<IsEmailDupRes>

    @POST("/api/users/nickname")
    fun postIsNicknameDup(@Body nickname: IsNicknameDupReq): Call<IsNicknameDupRes>
}
