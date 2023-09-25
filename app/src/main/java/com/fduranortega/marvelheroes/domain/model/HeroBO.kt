package com.fduranortega.marvelheroes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroBO(
    val id: Int,
    val name: String,
    val description: String,
    val urlImage: String,
    val comics: List<HeroExtraBO> = emptyList(),
    val numComics: Int,
) : Parcelable {
    fun hasDetailInfo() = comics.isNotEmpty()
}

@Parcelize
data class HeroExtraBO(
    val id: Int,
    val title: String,
    val description: String,
    val urlImage: String,
) : Parcelable