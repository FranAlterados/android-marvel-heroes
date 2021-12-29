package com.fduranortega.marvelheros.domain

import com.fduranortega.marvelheros.data.model.bo.HeroBO
import com.fduranortega.marvelheros.data.repository.HeroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeroUseCase @Inject constructor(
    private val heroRepository: HeroRepository,
) {

    suspend operator fun invoke(id: Int): Flow<HeroBO?> {
        return heroRepository.getHero(id)
    }
}