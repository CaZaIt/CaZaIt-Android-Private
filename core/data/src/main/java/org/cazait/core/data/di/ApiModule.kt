package org.cazait.core.data.di

import dagger.Provides
import org.cazait.core.data.api.auth.FavoriteApi
import org.cazait.core.data.api.auth.ReviewApi
import org.cazait.core.data.api.auth.TokenApi
import org.cazait.core.data.api.auth.UserAuthApi
import org.cazait.core.data.api.unauth.AuthApi
import org.cazait.core.data.api.unauth.CafeApi
import org.cazait.core.data.api.unauth.UserApi
import retrofit2.Retrofit
import javax.inject.Singleton

internal object ApiModule {
    @Provides
    @Singleton
    fun providesCafeService(
        retrofit: Retrofit,
    ): CafeApi {
        return retrofit.create(CafeApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUserService(
        retrofit: Retrofit,
    ): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthService(
        retrofit: Retrofit,
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesReviewServiceAuth(
        @Authenticated retrofit: Retrofit,
    ): ReviewApi {
        return retrofit.create(ReviewApi::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesFavoriteServiceAuth(
        @Authenticated retrofit: Retrofit,
    ): FavoriteApi {
        return retrofit.create(FavoriteApi::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesTokenServiceAuth(
        @Authenticated retrofit: Retrofit,
    ): TokenApi {
        return retrofit.create(TokenApi::class.java)
    }

    @Provides
    @Singleton
    @Authenticated
    fun providesUserServiceAuth(
        @Authenticated retrofit: Retrofit,
    ): UserAuthApi {
        return retrofit.create(UserAuthApi::class.java)
    }
}
