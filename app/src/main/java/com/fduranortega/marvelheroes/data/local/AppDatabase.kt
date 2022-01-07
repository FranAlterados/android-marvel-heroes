package com.fduranortega.marvelheroes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fduranortega.marvelheroes.data.model.ro.HeroExtraRO
import com.fduranortega.marvelheroes.data.model.ro.HeroRO

@Database(entities = [HeroRO::class, HeroExtraRO::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun heroDao(): HeroDao
}
