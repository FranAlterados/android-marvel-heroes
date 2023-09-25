package com.fduranortega.marvelheroes.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fduranortega.marvelheroes.domain.GetHeroUseCase
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
class DetailViewModel @Inject constructor(
    private val heroUseCase: GetHeroUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchHero(heroId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, hero = null, errorMessage = EMPTY_STRING)
            }

            try {
                viewModelScope.launch(Dispatchers.IO) {
                    heroUseCase(heroId).collect { heroBO ->
                        _uiState.update {
                            it.copy(hero = heroBO, isLoading = false)
                        }
                    }
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    val message = ioe.message ?: EMPTY_STRING
                    it.copy(isLoading = false, hero = null, errorMessage = message)
                }
            }
        }
    }
}