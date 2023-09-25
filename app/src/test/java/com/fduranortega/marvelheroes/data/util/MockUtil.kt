package com.fduranortega.marvelheroes.data.util

import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.domain.model.HeroExtraBO

object MockUtil {

    fun mockHero() = HeroBO(
        1011334,
        "3-D Man",
        "",
        "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
        emptyList(),
        12
    )

    fun mockHeroList() = listOf(mockHero())

    fun mockHeroExtra() = HeroExtraBO(
        1,
        "Hero extra title",
        "Hero extra description",
        "urlImage"
    )

    fun mockHeroId() = 1011334
}