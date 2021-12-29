package com.fduranortega.marvelheros.data.local

import com.fduranortega.marvelheros.data.model.bo.HeroBO
import com.fduranortega.marvelheros.data.model.mapper.toBO
import com.fduranortega.marvelheros.data.model.mapper.toRO
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
        return heroDao.getHero(id).toBO()
    }

    override suspend fun saveHeroList(heroList: List<HeroBO>, page: Int) {
        heroDao.insertHeroList(heroList.toRO(page))
    }

    override suspend fun saveHero(hero: HeroBO) {
        heroDao.insertHero(hero.toRO())
    }
}