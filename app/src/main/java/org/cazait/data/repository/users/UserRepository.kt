package org.cazait.data.repository.users

import kotlinx.coroutines.flow.Flow
import org.cazait.data.Resource
import org.cazait.data.model.request.IsEmailDupReq
import org.cazait.data.model.request.IsNicknameDupReq
import org.cazait.data.model.request.SignUpReq
import org.cazait.data.model.response.IsEmailDupRes
import org.cazait.data.model.response.IsNicknameDupRes
import org.cazait.data.model.response.SignUpRes

interface UserRepository {
    suspend fun signUp(body: SignUpReq): Flow<Resource<SignUpRes>>
    suspend fun isNicknameDup(body: IsNicknameDupReq): Flow<Resource<IsNicknameDupRes>>
    suspend fun isEmailDup(body: IsEmailDupReq): Flow<Resource<IsEmailDupRes>>
}
