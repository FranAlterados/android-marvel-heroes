package com.fduranortega.marvelheroes.data.repository

import com.fduranortega.marvelheroes.data.local.HeroLocalDataSource
import com.fduranortega.marvelheroes.data.model.bo.HeroBO
import com.fduranortega.marvelheroes.data.remote.HeroRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface HeroRepository {
    suspend fun getHeroList(page: Int): Flow<List<HeroBO>>
    suspend fun getHero(id: Int): Flow<HeroBO?>
}

class HeroRepositoryImpl @Inject constructor(
    private val heroRemoteDataSource: HeroRemoteDataSource,
    private val heroLocalDataSource: HeroLocalDataSource,
) : HeroRepository {
    override suspend fun getHeroList(page: Int) = flow {
        val localHeroList = heroLocalDataSource.getHeroList(page)
        if (localHeroList.isNotEmpty()) {
            emit(localHeroList)
        } else {
            val remoteHeroList = heroRemoteDataSource.getHeroList(page)
            remoteHeroList.collect {
                heroLocalDataSource.saveHeroList(it, page)
                emit(it)
            }
        }
    }

    override suspend fun getHero(id: Int) = flow {
        val localHero = heroLocalDataSource.getHero(id)
        if (localHero != null && localHero.hasDetailInfo()) {
            emit(localHero)
        } else {
            val remoteHero = heroRemoteDataSource.getHero(id)
            remoteHero.collect { hero ->
                hero?.let {
                    heroLocalDataSource.saveHero(it)
                }
                emit(hero)
            }
        }
    }
}