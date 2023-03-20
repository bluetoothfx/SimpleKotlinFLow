package com.blueprint.simplekotlinflow.di

import com.blueprint.simplekotlinflow.BuildConfig
import com.blueprint.simplekotlinflow.network.RestCall
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi
        .Builder()
        .run {
            add(KotlinJsonAdapterFactory())
            build()
        }

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient
            .connectTimeout(3000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
            .writeTimeout(3000, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient
            .Builder().connectTimeout(3000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
            .writeTimeout(3000, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun providesApiService(okHttpClient: OkHttpClient, moshi: Moshi): RestCall =
        Retrofit.Builder()
            .run {
                baseUrl(RestCall.BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(okHttpClient)
                build()
            }.create(RestCall::class.java)
}