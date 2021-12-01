package com.zalocoders.twitterapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zalocoders.twitterapp.data.api.ApiService
import com.zalocoders.twitterapp.utils.loggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    
    private const val BASE_URL = " https://api.twitter.com/2/tweets/search/"
    private const val TIME_OUT = 30
    
    
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .addInterceptor { chain ->
                    val request = chain.request()
                    val authRequest = request.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAG1fWAEAAAAAHcOKvBVtJVASmxmaFlRvDqNYDhU%3DBMvmmtB2efRlUUJ8hekrPWIraGRRWr3hbdQHAnVjn9tT6FDDxO")
                            .build()
                    return@addInterceptor chain.proceed(authRequest)
                }
                .addNetworkInterceptor(loggingInterceptor)
        return builder.build()
    }
    
    
    
    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
    
}

