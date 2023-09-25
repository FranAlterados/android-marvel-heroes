package com.fduranortega.marvelheroes.data.model.mapper

import com.fduranortega.marvelheroes.data.model.dto.HeroResponseDTO
import com.fduranortega.marvelheroes.data.model.dto.HeroThumbnailDTO
import com.fduranortega.marvelheroes.data.model.ro.HeroExtraRO
import com.fduranortega.marvelheroes.data.model.ro.HeroRO
import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.domain.model.HeroExtraBO
import com.fduranortega.marvelheroes.utils.EMPTY_STRING
import com.fduranortega.marvelheroes.utils.convertHttpToHttps


fun HeroThumbnailDTO.getUrlImage(): String {
    val fixedPath = path.convertHttpToHttps()
    return "$fixedPath.$extension"
}

fun HeroResponseDTO.toBO(): List<HeroBO> {
    return this.data.results.map { heroDTO ->
        HeroBO(
            heroDTO.id,
            heroDTO.name ?: EMPTY_STRING,
            heroDTO.description ?: EMPTY_STRING,
            heroDTO.thumbnail.getUrlImage(),
            numComics = heroDTO.comics?.available ?: 0
        )
    }
}

fun List<HeroBO>.toRO(page: Int): List<HeroRO> {
    return map { heroBO -> heroBO.toRO(page) }
}

fun HeroResponseDTO.toHeroExtraBO(): List<HeroExtraBO> {
    return this.data.results.map { heroDTO ->
        HeroExtraBO(
            heroDTO.id,
            heroDTO.title ?: EMPTY_STRING,
            heroDTO.description ?: EMPTY_STRING,
            heroDTO.thumbnail.getUrlImage()
        )
    }
}

fun HeroBO.toRO(page: Int): HeroRO {
    return HeroRO(
        id,
        name,
        description,
        urlImage,
        page,
        numComics,
    )
}

fun HeroRO.toBO(): HeroBO {
    return HeroBO(
        id,
        name,
        description,
        urlImage,
        numComics = numComics,
    )
}

fun List<HeroRO>.toBO(): List<HeroBO> {
    return map { heroRO -> heroRO.toBO() }
}

fun HeroExtraRO.toBO(): HeroExtraBO {
    return HeroExtraBO(id, title, description, urlImage)
}

fun HeroExtraBO.toRO(heroId: Int): HeroExtraRO {
    return HeroExtraRO(id, heroId, title, description, urlImage)
}

fun List<HeroExtraBO>.toHeroExtraRO(heroId: Int): List<HeroExtraRO> {
    return map { it.toRO(heroId) }
}