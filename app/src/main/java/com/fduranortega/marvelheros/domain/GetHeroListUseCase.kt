package com.fduranortega.marvelheros.domain

import com.fduranortega.marvelheros.data.model.bo.HeroBO
import com.fduranortega.marvelheros.data.repository.HeroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeroListUseCase @Inject constructor(
    private val heroRepository: HeroRepository,
) {

    suspend operator fun invoke(page: Int): Flow<List<HeroBO>> {
        return heroRepository.getHeroList(page)
    }
}