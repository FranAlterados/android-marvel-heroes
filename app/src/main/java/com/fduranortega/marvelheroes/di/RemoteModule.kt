package com.fduranortega.marvelheroes.di

import com.fduranortega.marvelheroes.data.remote.HeroClient
import com.fduranortega.marvelheroes.data.remote.HeroRemoteDataSource
import com.fduranortega.marvelheroes.data.remote.HeroRemoteDataSourceImpl
import com.fduranortega.marvelheroes.data.remote.HeroService
import com.fduranortega.marvelheroes.data.remote.HttpRequestInterceptor
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideHeroRemoteDataSource(heroClient: HeroClient): HeroRemoteDataSource {
        return HeroRemoteDataSourceImpl(heroClient)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gateway.marvel.com:443/v1/public/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHeroService(retrofit: Retrofit): HeroService {
        return retrofit.create(HeroService::class.java)
    }

    @Provides
    @Singleton
    fun provideHeroClient(heroService: HeroService): HeroClient {
        return HeroClient(heroService)
    }
}