package org.cazait.data.repository.users

interface UserRepository {
    suspend fun signUp()
    suspend fun isNicknameDup()
    suspend fun isEmailDup()
}