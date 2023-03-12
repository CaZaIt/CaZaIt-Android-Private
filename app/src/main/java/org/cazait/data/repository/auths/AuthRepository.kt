package org.cazait.data.repository.auths

interface AuthRepository {
    suspend fun signIn()
    suspend fun refreshToken()
}