package com.fduranortega.marvelheroes.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.fduranortega.marvelheroes.data.model.bo.HeroBO
import com.fduranortega.marvelheroes.data.model.bo.HeroExtraBO
import com.fduranortega.marvelheroes.data.model.mapper.toBO
import com.fduranortega.marvelheroes.data.model.mapper.toRO
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class HeroDaoTest {

    lateinit var db: AppDatabase
    private lateinit var heroDao: HeroDao

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        heroDao = db.heroDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    fun mockHero() = HeroBO(
        1011334,
        "3-D Man",
        "",
        "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
        emptyList(),
        12
    )

    @Test
    fun insertAndLoadHeroListTest() = runBlocking {
        val mockPage = 0
        val heroListBO = listOf(mockHero())
        heroDao.insertHeroList(heroListBO.toRO(mockPage))

        val loadFromDB = heroDao.getHeroList(mockPage)

        MatcherAssert.assertThat(loadFromDB.toBO(), CoreMatchers.`is`(heroListBO))
        MatcherAssert.assertThat(loadFromDB[0].toBO(), CoreMatchers.`is`(mockHero()))
    }

    @Test
    fun loadHeroTest() = runBlocking {
        val mockPage = 0
        heroDao.insertHeroList(listOf(mockHero()).toRO(mockPage))

        val loadFromDB = heroDao.getHero(1011334)

        MatcherAssert.assertThat(loadFromDB.toBO(), CoreMatchers.`is`(mockHero()))
    }

    @Test
    fun insertAndLoadHeroExtra() = runBlocking {
        val heroExtraBO = HeroExtraBO(
            1,
            "Hero extra title",
            "Hero extra description",
            "urlImage"
        )
        val mockPage = 0
        heroDao.insertHeroList(listOf(mockHero()).toRO(mockPage))
        heroDao.insertHeroExtra(listOf(heroExtraBO.toRO(1011334)))

        val loadFromDB = heroDao.getHeroExtra(1011334)

        MatcherAssert.assertThat(loadFromDB[0].toBO(), CoreMatchers.`is`(heroExtraBO))
    }
}