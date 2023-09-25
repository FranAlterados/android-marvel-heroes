package com.fduranortega.marvelheroes.data.local

import com.fduranortega.marvelheroes.data.model.mapper.toBO
import com.fduranortega.marvelheroes.data.model.mapper.toHeroExtraRO
import com.fduranortega.marvelheroes.data.model.mapper.toRO
import com.fduranortega.marvelheroes.domain.model.HeroBO
import javax.inject.Inject

interface HeroLocalDataSource {
    suspend fun getHeroList(page: Int): List<HeroBO>
    suspend fun getHero(id: Int): HeroBO?
    suspend fun saveHeroList(heroList: List<HeroBO>, page: Int)
    suspend fun saveHero(hero: HeroBO)
}

class HeroLocalDataSourceImpl @Inject constructor(
    private val heroDao: HeroDao
) : HeroLocalDataSource {
    override suspend fun getHeroList(page: Int): List<HeroBO> {
        return heroDao.getHeroList(page).toBO()
    }

    override suspend fun getHero(id: Int): HeroBO? {
        val heroBO = heroDao.getHero(id).toBO()
        val heroExtraBO = heroDao.getHeroExtra(id).map { it.toBO() }
        return heroBO.copy(comics = heroExtraBO)
    }

    override suspend fun saveHeroList(heroList: List<HeroBO>, page: Int) {
        heroDao.insertHeroList(heroList.toRO(page))
    }

    override suspend fun saveHero(hero: HeroBO) {
        heroDao.insertHeroExtra(hero.comics.toHeroExtraRO(hero.id))
    }
}