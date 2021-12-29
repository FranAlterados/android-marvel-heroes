package com.fduranortega.marvelheros.ui.main

import com.fduranortega.marvelheros.data.model.bo.HeroBO

data class MainUiState(
    val isLoading: Boolean = false,
    val heroList: List<HeroBO> = listOf(),
    val errorMessage: String = "",
)