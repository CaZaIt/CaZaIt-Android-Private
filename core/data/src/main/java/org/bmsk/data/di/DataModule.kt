package org.bmsk.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bmsk.data.repository.AuthRepository
import org.bmsk.data.repository.AuthRepositoryImpl
import org.bmsk.data.repository.CafeRepository
import org.bmsk.data.repository.CafeRepositoryImpl
import org.bmsk.data.repository.UserRepository
import org.bmsk.data.repository.UserRepositoryImpl

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

    @Binds
    fun provideCafesRepository(
        cafeRepositoryImpl: CafeRepositoryImpl,
    ): CafeRepository
}
