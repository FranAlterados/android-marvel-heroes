package com.fduranortega.marvelheros.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fduranortega.marvelheros.data.model.ro.HeroRO

@Dao
interface HeroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroList(heroList: List<HeroRO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(hero: HeroRO)

    @Query("SELECT * FROM HeroRO WHERE page = :page")
    suspend fun getHeroList(page: Int): List<HeroRO>

    @Query("SELECT * FROM HeroRO WHERE id = :id")
    suspend fun getHero(id: Int): HeroRO
}