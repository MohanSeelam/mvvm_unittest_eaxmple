package com.android.mvvmunittesteaxmple.di

import android.content.Context
import com.android.mvvmunittesteaxmple.BuildConfig
import com.android.mvvmunittesteaxmple.core.AppDispatcherProvider
import com.android.mvvmunittesteaxmple.core.DispatcherProvider
import com.android.mvvmunittesteaxmple.data.repository.PostsRepositoryImp
import com.android.mvvmunittesteaxmple.data.source.remote.ApiService
import com.android.mvvmunittesteaxmple.domain.repository.PostApiInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIME_OUT = 30L


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        {  message->
//            Timber.tag("OkHttp").d(message)
//        }
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun createRetrofit(
        okHttpClient: OkHttpClient,
        url: String,
        responseWrapper: ResponseWrapper,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(responseWrapper)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    @Singleton
    fun createService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }



    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }


    @Provides
    @Singleton
    fun createPostRepository(apiService: ApiService): PostApiInterface {
        return PostsRepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun createDispatchProvider(): DispatcherProvider {
        return AppDispatcherProvider()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }


@Provides
@Singleton
fun provideContext(@ApplicationContext appContext: Context)= appContext
}