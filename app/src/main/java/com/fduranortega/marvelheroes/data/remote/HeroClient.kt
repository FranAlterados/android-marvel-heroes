package com.fduranortega.marvelheroes.data.remote

import com.fduranortega.marvelheroes.data.model.dto.HeroResponseDTO
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class HeroClient @Inject constructor(
    private val heroService: HeroService
) {
    suspend fun fetchHeroList(
        page: Int
    ): ApiResponse<HeroResponseDTO> =
        heroService.fetchHeroList(
            limit = PAGING_SIZE,
            offset = page * PAGING_SIZE
        )

    suspend fun fetchHero(
        id: Int
    ): ApiResponse<HeroResponseDTO> =
        heroService.fetchHero(
            id = id
        )

    suspend fun fetchHeroExtra(url: String): ApiResponse<HeroResponseDTO> = heroService.fetchHeroExtra(url)

    companion object {
        private const val PAGING_SIZE = 20
    }
}