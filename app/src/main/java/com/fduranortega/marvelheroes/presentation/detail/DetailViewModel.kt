package com.fduranortega.marvelheroes.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fduranortega.marvelheroes.domain.GetHeroUseCase
import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.utils.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed class DetailViewState {
    data object ShowLoading : DetailViewState()
    data class ShowHero(val heroBO: HeroBO) : DetailViewState()
    data class ShowError(val message: String) : DetailViewState()
}

sealed class DetailEvent {
    class FetchHero(val heroId: Int) : DetailEvent()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val heroUseCase: GetHeroUseCase
) : ViewModel() {

    private val mutableViewState = MutableStateFlow<DetailViewState>(DetailViewState.ShowLoading)
    val viewState: StateFlow<DetailViewState> = mutableViewState.asStateFlow()

    fun dispatch(event: DetailEvent) {
        when (event) {
            is DetailEvent.FetchHero -> fetchHero(event.heroId)
        }
    }

    private fun fetchHero(heroId: Int) {
        viewModelScope.launch {
            mutableViewState.update { DetailViewState.ShowLoading }
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    heroUseCase(heroId).collect { heroBO ->
                        heroBO?.let {
                            mutableViewState.update {
                                DetailViewState.ShowHero(heroBO)
                            }
                        } ?: throw IOException("Error fetching hero")
                    }
                }
            } catch (ioe: IOException) {
                mutableViewState.update {
                    val message = ioe.message ?: EMPTY_STRING
                    DetailViewState.ShowError(message)
                }
            }
        }
    }
}