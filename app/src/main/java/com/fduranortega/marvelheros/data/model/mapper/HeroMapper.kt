package com.fduranortega.marvelheros.data.model.mapper

import com.fduranortega.marvelheros.data.model.bo.HeroBO
import com.fduranortega.marvelheros.data.model.dto.HeroResponseDTO
import com.fduranortega.marvelheros.data.model.dto.HeroThumbnailDTO
import com.fduranortega.marvelheros.data.model.ro.HeroRO

const val HTTP_LABEL = "http:"
const val HTTPS_LABEL = "https:"

fun HeroThumbnailDTO.getUrlImage(): String {
    val fixedPath = if (path.startsWith(HTTPS_LABEL)) {
        path
    } else {
        path.replace(HTTP_LABEL, HTTPS_LABEL)
    }
    return "$fixedPath.$extension"
}

fun HeroResponseDTO.toBO(): List<HeroBO> {
    return this.data.results.map { heroDTO ->
        HeroBO(heroDTO.id, heroDTO.name, heroDTO.description, heroDTO.thumbnail.getUrlImage())
    }
}

fun List<HeroBO>.toRO(page: Int): List<HeroRO> {
    return map { heroBO -> heroBO.toRO(page) }
}

fun HeroBO.toRO(page: Int = -1): HeroRO {
    return HeroRO(id, name, description, urlImage, page)
}

fun HeroRO.toBO(): HeroBO {
    return HeroBO(id, name, description, urlImage)
}

fun List<HeroRO>.toBO(): List<HeroBO> {
    return map { heroRO -> heroRO.toBO() }
}