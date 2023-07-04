package org.cazait.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.network.api.AuthService
import org.cazait.network.api.CafeService
import org.cazait.network.api.UserService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    @Unauthenticated
    fun providesCafeService(
        @Unauthenticated retrofit: Retrofit
    ): CafeService {
        return retrofit.create(CafeService::class.java)
    }

    @Provides
    @Singleton
    @Unauthenticated
    fun providesUserService(
        @Unauthenticated retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    @Unauthenticated
    fun providesAuthService(
        @Unauthenticated retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesCafeServiceAuth(
        @Authenticated retrofit: Retrofit
    ): CafeService {
        return retrofit.create(CafeService::class.java)
    }

//    @Provides
//    @Singleton
//    @Authenticated
//    fun providesUserServiceAuth(
//        @Authenticated retrofit: Retrofit
//    ): UserService {
//        return retrofit.create(UserService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    @Authenticated
//    fun providesAuthServiceAuth(
//        @Authenticated retrofit: Retrofit
//    ): AuthService {
//        return retrofit.create(AuthService::class.java)
//    }
}