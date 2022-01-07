package com.fduranortega.marvelheroes.data.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeroDTO(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "title") val title: String?,
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "description") val description: String?,
        @field:Json(name = "thumbnail") val thumbnail: HeroThumbnailDTO,
        @field:Json(name = "comics") val comics: HeroExtraDTO?,
)

@JsonClass(generateAdapter = true)
data class HeroThumbnailDTO(
        @field:Json(name = "path") val path: String,
        @field:Json(name = "extension") val extension: String,
)

@JsonClass(generateAdapter = true)
data class HeroDataDTO(
        @field:Json(name = "results") val results: List<HeroDTO>,
)

@JsonClass(generateAdapter = true)
data class HeroResponseDTO(
        @field:Json(name = "code") val code: Int,
        @field:Json(name = "status") val status: String,
        @field:Json(name = "data") val data: HeroDataDTO,
)

@JsonClass(generateAdapter = true)
data class HeroExtraDTO(
        @field:Json(name = "collectionURI") val collectionURI: String,
        @field:Json(name = "available") val available: Int,
)
