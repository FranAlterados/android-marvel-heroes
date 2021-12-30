package com.fduranortega.marvelheros.data.remote

import com.fduranortega.marvelheros.data.model.dto.HeroResponseDTO
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeroService {

    @GET("characters")
    suspend fun fetchHeroList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): ApiResponse<HeroResponseDTO>

    @GET("characters/{characterId}")
    suspend fun fetchHero(@Path("characterId") id: Int): ApiResponse<HeroResponseDTO>
}