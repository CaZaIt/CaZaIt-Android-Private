package org.cazait.data.repository.users

interface UsersRepository {
    suspend fun signUp()
    suspend fun isNicknameDup()
    suspend fun isEmailDup()
}