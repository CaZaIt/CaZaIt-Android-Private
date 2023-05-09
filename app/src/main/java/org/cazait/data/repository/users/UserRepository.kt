package org.cazait.data.repository.users

import kotlinx.coroutines.flow.Flow
import org.cazait.data.Resource
import org.cazait.data.dto.request.IsEmailDupReq
import org.cazait.data.dto.request.IsNicknameDupReq
import org.cazait.data.dto.request.SignUpReq
import org.cazait.data.dto.response.IsEmailDupRes
import org.cazait.data.dto.response.IsNicknameDupRes
import org.cazait.data.dto.response.SignUpRes

interface UserRepository {
    suspend fun signUp(body: SignUpReq): Flow<Resource<SignUpRes>>
    suspend fun isNicknameDup(body: IsNicknameDupReq): Flow<Resource<IsNicknameDupRes>>
    suspend fun isEmailDup(body: IsEmailDupReq): Flow<Resource<IsEmailDupRes>>
}
