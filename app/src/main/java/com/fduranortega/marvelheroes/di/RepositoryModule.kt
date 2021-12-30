package com.fduranortega.marvelheroes.di

import com.fduranortega.marvelheroes.data.local.HeroLocalDataSource
import com.fduranortega.marvelheroes.data.remote.HeroRemoteDataSource
import com.fduranortega.marvelheroes.data.repository.HeroRepository
import com.fduranortega.marvelheroes.data.repository.HeroRepositoryImpl
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
