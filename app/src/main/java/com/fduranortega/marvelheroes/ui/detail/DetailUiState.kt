package com.fduranortega.marvelheroes.ui.detail

import com.fduranortega.marvelheroes.data.model.bo.HeroBO
import com.fduranortega.marvelheroes.utils.EMPTY_STRING

data class DetailUiState(
    val isLoading: Boolean = false,
    val hero: HeroBO? = null,
    val errorMessage: String = EMPTY_STRING,
)