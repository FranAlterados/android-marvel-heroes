package com.fduranortega.marvelheroes.data.model.ro

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class HeroRO(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val urlImage: String,
    val page: Int,
    val numComics: Int,
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = HeroRO::class,
        parentColumns = ["id"],
        childColumns = ["heroId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HeroExtraRO(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "heroId", index = true) val heroId: Int,
    val title: String,
    val description: String,
    val urlImage: String,
)