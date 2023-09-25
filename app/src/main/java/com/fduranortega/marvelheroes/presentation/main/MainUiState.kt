package com.fduranortega.marvelheroes.presentation.main

import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.utils.EMPTY_STRING

data class MainUiState(
    val isLoading: Boolean = false,
    val heroList: List<HeroBO> = listOf(),
    val errorMessage: String = EMPTY_STRING,
)