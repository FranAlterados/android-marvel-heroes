package com.fduranortega.marvelheros.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fduranortega.marvelheros.data.model.ro.HeroRO

@Database(entities = [HeroRO::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun heroDao(): HeroDao
}
