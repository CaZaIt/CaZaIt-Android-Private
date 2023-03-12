package org.cazait.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.data.repository.auth.AuthRepository
import org.cazait.data.repository.auth.AuthRepositoryImpl
import org.cazait.data.repository.users.UserRepository
import org.cazait.data.repository.users.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    // auths
    @Binds
    fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository
}
