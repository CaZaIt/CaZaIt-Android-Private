package org.cazait.data.api

import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.SignUpRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/api/users/sign-up")
    fun postSignUp(@Body signUpRequest: SignUpReq): Call<SignUpRes>

    @GET("/api/users/email")
    fun getIsDuplicateEmail(
        @Query("email") email: String = "",
    ): Call<IsEmailDupRes>
}
