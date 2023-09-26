package com.fduranortega.marvelheroes.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fduranortega.marvelheroes.domain.GetHeroListUseCase
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

sealed class MainViewState {
    data class ShowHeroList(val loading: Boolean, val heroList: List<HeroBO>) : MainViewState()
    data class OnHeroClicked(val hero: HeroBO) : MainViewState()
    object ShowEmptyList : MainViewState()
    data class ShowError(val message: String) : MainViewState()
}

sealed class MainEvent {
    object OnResume : MainEvent()
    object FetchMoreHeroes : MainEvent()
    class OnClickHero(val hero: HeroBO) : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val heroListUseCase: GetHeroListUseCase
) : ViewModel() {

    private val mutableViewState = MutableStateFlow<MainViewState>(
        MainViewState.ShowHeroList(true, emptyList())
    )
    val viewState: StateFlow<MainViewState> = mutableViewState.asStateFlow()

    private var currentList: MutableList<HeroBO> = mutableListOf()
    var page = 0

    fun dispatch(event: MainEvent) {
        when (event) {
            MainEvent.FetchMoreHeroes -> fetchMoreHeroes()
            is MainEvent.OnClickHero -> onClickHero(event.hero)
            MainEvent.OnResume -> onResume()
        }
    }

    private fun onResume() {
        mutableViewState.update {
            MainViewState.ShowHeroList(false, currentList)
        }
    }

    private fun fetchMoreHeroes() {
        viewModelScope.launch {
            mutableViewState.update { MainViewState.ShowHeroList(true, currentList) }
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    heroListUseCase(page).collect { heroList ->
                        mutableViewState.update {
                            currentList.addAll(heroList)
                            if (currentList.isEmpty()) {
                                MainViewState.ShowEmptyList
                            } else {
                                MainViewState.ShowHeroList(false, currentList)
                            }
                        }
                    }
                }

            } catch (ioe: IOException) {
                mutableViewState.update {
                    val message = ioe.message ?: EMPTY_STRING
                    MainViewState.ShowError(message)
                }
            }
            page++
        }
    }

    private fun onClickHero(hero: HeroBO) {
        mutableViewState.update {
            MainViewState.OnHeroClicked(hero)
        }
    }
}
