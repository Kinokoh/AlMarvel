package com.chaliez.alma.test.data.network.di

import android.app.Application
import com.chaliez.alma.test.data.network.AuthentGetUrlInterceptor
import com.chaliez.alma.test.data.network.retrofit.MarvelService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return "https://gateway.marvel.com"
    }

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideAuthentInterceptor(): AuthentGetUrlInterceptor = AuthentGetUrlInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    fun provideOkhttpClient(authentInterceptor: AuthentGetUrlInterceptor, cache: Cache, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
            .addInterceptor(authentInterceptor)
            .addInterceptor(loggingInterceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideConverter(): Converter.Factory = MoshiConverterFactory.create(
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    )

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String,
                        converter: Converter.Factory,
                        client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun getApiClient(retrofit: Retrofit): MarvelService {
        return retrofit.create(MarvelService::class.java)
    }
}