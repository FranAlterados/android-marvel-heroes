package com.fduranortega.marvelheroes.presentation.detail

import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.utils.EMPTY_STRING

data class DetailUiState(
    val isLoading: Boolean = false,
    val hero: HeroBO? = null,
    val errorMessage: String = EMPTY_STRING,
)