package org.cazait.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.network.api.auth.FavoriteService
import org.cazait.network.api.unauth.AuthService
import org.cazait.network.api.auth.ReviewService
import org.cazait.network.api.auth.TokenService
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
    fun providesReviewServiceAuth(
        @Authenticated retrofit: Retrofit
    ): ReviewService {
        return retrofit.create(ReviewService::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesFavoriteServiceAuth(
        @Authenticated retrofit: Retrofit
    ): FavoriteService {
        return retrofit.create(FavoriteService::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesTokenServiceAuth(
        @Authenticated retrofit: Retrofit
    ): TokenService {
        return retrofit.create(TokenService::class.java)
    }
}