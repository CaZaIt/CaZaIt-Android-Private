package org.cazait.data.repository.users

import kotlinx.coroutines.flow.Flow
import org.cazait.data.Resource
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.SignUpRes

interface UserRepository {
    suspend fun signUp(body: SignUpReq): Flow<Resource<SignUpRes>>
    suspend fun isNicknameDup()
    suspend fun isEmailDup()
}
