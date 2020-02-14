package org.kikermo.tube.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .create()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(queryAppenderInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(queryAppenderInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun queryAppenderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newUrl = chain.request().url().newBuilder()
                .addQueryParameter(APPLICATION_ID_NAME, APPLICATION_ID_VALUE)
                .addQueryParameter(APPLICATION_KEY_NAME, APPLICATION_KEY_VALUE)
                .build()
            val newRequest = chain.request().newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
    }

    //Ideally this would go on configuration
    companion object {
        const val BASE_URL = "https://api.tfl.gov.uk"
        const val APPLICATION_ID_NAME = "app_id"
        const val APPLICATION_ID_VALUE = "2d0ab855"
        const val APPLICATION_KEY_NAME = "app_key"
        const val APPLICATION_KEY_VALUE = "0286d36e5749792a49b6dc20ba0d6385"
    }
}
