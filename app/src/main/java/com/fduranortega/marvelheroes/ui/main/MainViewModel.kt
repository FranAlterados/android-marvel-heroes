package com.fduranortega.marvelheroes.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fduranortega.marvelheroes.domain.GetHeroListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    var page = 0

    private var fetchJob: Job? = null

    fun fetchMoreHeroes() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                heroListUseCase(page).collect { heroList ->
                    _uiState.update {
                        it.copy(heroList = heroList, isLoading = false)
                    }
                }

            } catch (ioe: IOException) {
                _uiState.update {
                    val message = ioe.message ?: ""
                    it.copy(errorMessage = message)
                }
            }
            page++
        }
    }

}