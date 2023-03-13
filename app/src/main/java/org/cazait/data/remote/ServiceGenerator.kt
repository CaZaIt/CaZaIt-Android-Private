package org.cazait.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.cazait.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceGenerator @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit
    private val gson = GsonBuilder().setLenient().create()

    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
            .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .method(original.method, original.body)
            .build()

        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    companion object {
        private const val TIMEOUT_READ = 30 // In seconds
        private const val CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_VALUE = "application/json"
        private const val ACCESS_TOKEN = "Authorization"
        private const val TIMEOUT_CONNECT = 30 // In seconds
        private const val BASE_URL = "https://cazait.shop"
    }
}
