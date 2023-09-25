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
    fun insertHeroList(heroList: List<HeroRO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeroExtra(heroExtraList: List<HeroExtraRO>)

    @Query("SELECT * FROM HeroExtraRO WHERE heroId = :id")
    fun getHeroExtra(id: Int): List<HeroExtraRO>

    @Query("SELECT * FROM HeroRO WHERE page = :page")
    fun getHeroList(page: Int): List<HeroRO>

    @Query("SELECT * FROM HeroRO WHERE id = :id")
    fun getHero(id: Int): HeroRO
}