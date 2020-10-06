package com.example.myapplication.di

import com.example.myapplication.network.ApiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module()
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @IntoSet
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @IntoSet
    fun provideStethoInterceptor(): Interceptor = StethoInterceptor()

    @Provides
    fun provideOkHttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
        dataStore: PrefDataStore
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
                //cookieJar(JavaNetCookieJar(CookieManager.getDefault()))
                interceptors.forEach { interceptor ->
                    addNetworkInterceptor(interceptor)
                }
                addInterceptor { chain ->
                    val token = runBlocking { dataStore.getToken().first() }
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .addHeader("User-Agent", "Android")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", "$token")
                            .build()
                    )
                }
            }
            .build()
    }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideAdapterFactory(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()


    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        retrofitAdapter: RxJava3CallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(" https://pokeapi.co/api/v2/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(retrofitAdapter)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}