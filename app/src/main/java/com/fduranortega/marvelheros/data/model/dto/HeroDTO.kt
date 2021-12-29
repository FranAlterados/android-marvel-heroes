package com.fduranortega.marvelheros.data.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeroDTO(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "description") val description: String,
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
