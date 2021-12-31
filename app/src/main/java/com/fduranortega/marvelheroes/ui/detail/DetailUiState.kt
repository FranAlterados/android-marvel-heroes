package com.fduranortega.marvelheroes.ui.detail

import com.fduranortega.marvelheroes.data.model.bo.HeroBO

data class DetailUiState(
    val isLoading: Boolean = false,
    val hero: HeroBO? = null,
    val errorMessage: String = "",
)