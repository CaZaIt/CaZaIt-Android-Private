package org.bmsk.data.repository

import kotlinx.coroutines.flow.Flow
import org.cazait.model.IdDup
import org.cazait.model.NicknameDup
import org.cazait.model.Resource
import org.cazait.model.SignUpInfo
import org.cazait.model.local.UserPreference

interface UserRepository {
    suspend fun signUp(
        userId: String,
        password: String,
        phoneNumber: String,
        nickname: String
    ): Flow<Resource<SignUpInfo>>

    suspend fun isUserIdDup(userId: String): Flow<Resource<IdDup>>
    suspend fun isNicknameDup(nickname: String): Flow<Resource<NicknameDup>>
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun getUserInfo(): Flow<UserPreference>
    suspend fun signOut()
}
