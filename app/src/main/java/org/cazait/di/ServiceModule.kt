package org.cazait.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.data.api.AuthService
import org.cazait.data.api.CafeService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesCafeService(
        retrofit: Retrofit
    ): CafeService {
        return retrofit.create(CafeService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthService(
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}