package com.fduranortega.marvelheroes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fduranortega.marvelheroes.data.model.ro.HeroExtraRO
import com.fduranortega.marvelheroes.data.model.ro.HeroRO

@Dao
interface HeroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroList(heroList: List<HeroRO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroExtra(heroExtraList: List<HeroExtraRO>)

    @Query("SELECT * FROM HeroExtraRO WHERE heroId = :id")
    suspend fun getHeroExtra(id: Int): List<HeroExtraRO>

    @Query("SELECT * FROM HeroRO WHERE page = :page")
    suspend fun getHeroList(page: Int): List<HeroRO>

    @Query("SELECT * FROM HeroRO WHERE id = :id")
    suspend fun getHero(id: Int): HeroRO
}