package com.fduranortega.marvelheroes.domain

import com.fduranortega.marvelheroes.data.model.bo.HeroBO
import com.fduranortega.marvelheroes.data.repository.HeroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeroListUseCase @Inject constructor(
    private val heroRepository: HeroRepository,
) {

    suspend operator fun invoke(page: Int): Flow<List<HeroBO>> {
        return heroRepository.getHeroList(page)
    }
}