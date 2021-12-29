package com.fduranortega.marvelheros.data.model.ro

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HeroRO(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val urlImage: String,
    val page: Int,
)