package com.fduranortega.marvelheroes.ui.main

import com.fduranortega.marvelheroes.data.model.bo.HeroBO
import com.fduranortega.marvelheroes.utils.EMPTY_STRING

data class MainUiState(
    val isLoading: Boolean = false,
    val heroList: List<HeroBO> = listOf(),
    val errorMessage: String = EMPTY_STRING,
)