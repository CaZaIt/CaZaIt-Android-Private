package org.cazait.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.network.api.unauth.AuthService
import org.cazait.network.api.auth.ReviewService
import org.cazait.network.api.unauth.CafeService
import org.cazait.network.api.unauth.UserService
import retrofit2.Retrofit
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
    fun providesUserService(
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthService(
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesCafeServiceAuth(
        @Authenticated retrofit: Retrofit
    ): ReviewService {
        return retrofit.create(ReviewService::class.java)
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