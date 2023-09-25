package com.fduranortega.marvelheroes.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fduranortega.marvelheroes.domain.GetHeroListUseCase
import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.utils.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val heroListUseCase: GetHeroListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var currentList: MutableList<HeroBO> = mutableListOf()
    var page = 0

    private var fetchJob: Job? = null

    fun fetchMoreHeroes() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, heroList = emptyList(), errorMessage = EMPTY_STRING)
            }

            try {
                viewModelScope.launch(Dispatchers.IO) {
                    heroListUseCase(page).collect { heroList ->
                        _uiState.update {
                            currentList.addAll(heroList)
                            it.copy(
                                heroList = currentList,
                                isLoading = false,
                                errorMessage = EMPTY_STRING
                            )
                        }
                    }
                }

            } catch (ioe: IOException) {
                _uiState.update {
                    val message = ioe.message ?: EMPTY_STRING
                    it.copy(isLoading = false, heroList = emptyList(), errorMessage = message)
                }
            }
            page++
        }
    }

}