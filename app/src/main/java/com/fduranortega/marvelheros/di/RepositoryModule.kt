package com.fduranortega.marvelheros.di

import com.fduranortega.marvelheros.data.local.HeroLocalDataSource
import com.fduranortega.marvelheros.data.remote.HeroRemoteDataSource
import com.fduranortega.marvelheros.data.repository.HeroRepository
import com.fduranortega.marvelheros.data.repository.HeroRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideHeroRepository(
    heroRemoteDataSource: HeroRemoteDataSource,
    heroLocalDataSource: HeroLocalDataSource,
  ): HeroRepository {
    return HeroRepositoryImpl(heroRemoteDataSource, heroLocalDataSource)
  }
}
