package org.cazait.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.data.repository.auths.AuthRepository
import org.cazait.data.repository.auths.AuthRepositoryImpl
import org.cazait.data.repository.users.UsersRepository
import org.cazait.data.repository.users.UsersRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun provideUsersRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository


    // auths
    @Binds
    fun provideAuthsRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
