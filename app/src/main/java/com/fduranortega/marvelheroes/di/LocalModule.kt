package com.fduranortega.marvelheroes.di

import android.app.Application
import androidx.room.Room
import com.fduranortega.marvelheroes.data.local.AppDatabase
import com.fduranortega.marvelheroes.data.local.HeroDao
import com.fduranortega.marvelheroes.data.local.HeroLocalDataSource
import com.fduranortega.marvelheroes.data.local.HeroLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideHeroLocalDataSource(heroDao: HeroDao): HeroLocalDataSource {
        return HeroLocalDataSourceImpl(heroDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "Hero.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHeroDao(appDatabase: AppDatabase): HeroDao {
        return appDatabase.heroDao()
    }
}