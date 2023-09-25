package com.fduranortega.marvelheroes.domain

import com.fduranortega.marvelheroes.data.repository.HeroRepository
import com.fduranortega.marvelheroes.domain.model.HeroBO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeroListUseCase @Inject constructor(
    private val heroRepository: HeroRepository,
) {

    suspend operator fun invoke(page: Int): Flow<List<HeroBO>> {
        return heroRepository.getHeroList(page)
    }
}