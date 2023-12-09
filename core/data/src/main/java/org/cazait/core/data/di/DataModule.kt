package org.cazait.core.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.core.data.repository.AuthRepository
import org.cazait.core.data.repository.AuthRepositoryImpl
import org.cazait.core.data.repository.CafeRepository
import org.cazait.core.data.repository.CafeRepositoryImpl
import org.cazait.core.data.repository.UserRepository
import org.cazait.core.data.repository.UserRepositoryImpl
import org.cazait.datastore.data.repository.UserPreferenceRepository
import javax.inject.Singleton

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
