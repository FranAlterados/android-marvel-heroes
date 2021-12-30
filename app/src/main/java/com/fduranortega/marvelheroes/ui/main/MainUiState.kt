package com.fduranortega.marvelheroes.ui.main

import com.fduranortega.marvelheroes.data.model.bo.HeroBO

data class MainUiState(
    val isLoading: Boolean = false,
    val heroList: List<HeroBO> = listOf(),
    val errorMessage: String = "",
)