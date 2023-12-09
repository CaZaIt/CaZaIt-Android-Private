package org.cazait.core.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.cazait.core.data.util.NetworkResultCallAdapterFactory
import org.cazait.datastore.data.repository.UserPreferenceRepository
import org.cazait.model.local.UserPreference
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        userPreferenceRepository: UserPreferenceRepository,
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val user = runBlocking {
                        runCatching {
                            userPreferenceRepository.getUserPreference().first()
                        }.getOrDefault(UserPreference.createDefaultInstance())
                    }
                    val token = user.accessToken
                    val request = chain.request().newBuilder()
                        .addHeader(AUTHORIZATION, "Bearer $token")
                        .build()

                    chain.proceed(request)
                },
            )
            addInterceptor(interceptor)
//            if (BuildConfig.DEBUG) {
//                addNetworkInterceptor(httpLoggingInterceptor)
//            }
        }
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build(),
    )

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder().baseUrl(URL)
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    companion object {
        const val URL = "https://cazait.shop"
        const val AUTHORIZATION = "Authorization"
    }
}
