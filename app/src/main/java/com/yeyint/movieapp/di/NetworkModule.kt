package com.yeyint.movieapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yeyint.movieapp.data.MovieApiService
import com.yeyint.movieapp.common.Constant.Companion.BASE_URL
import com.yeyint.movieapp.data.remote.MovieRemoteDataSource
import com.yeyint.movieapp.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*@Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MovieRetrofit*/

    @Singleton
    @Provides
    fun provideHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) : MovieApiService =
        retrofit.create(MovieApiService::class.java)
}