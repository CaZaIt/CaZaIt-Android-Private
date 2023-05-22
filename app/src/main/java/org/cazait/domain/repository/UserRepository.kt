package org.cazait.domain.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.data.dto.request.IsEmailDupReq
import org.cazait.data.dto.request.IsNicknameDupReq
import org.cazait.data.dto.request.SignUpReq
import org.cazait.domain.model.EmailDup
import org.cazait.domain.model.NicknameDup
import org.cazait.domain.model.Resource
import org.cazait.domain.model.SignUp

interface UserRepository {
    suspend fun signUp(body: SignUpReq): Flow<Resource<SignUp>>
    suspend fun isNicknameDup(body: IsNicknameDupReq): Flow<Resource<NicknameDup>>
    suspend fun isEmailDup(body: IsEmailDupReq): Flow<Resource<EmailDup>>
}
