package com.fduranortega.marvelheroes.domain

import com.fduranortega.marvelheroes.data.model.bo.HeroBO
import com.fduranortega.marvelheroes.data.repository.HeroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeroUseCase @Inject constructor(
    private val heroRepository: HeroRepository,
) {

    suspend operator fun invoke(id: Int): Flow<HeroBO?> {
        return heroRepository.getHero(id)
    }
}