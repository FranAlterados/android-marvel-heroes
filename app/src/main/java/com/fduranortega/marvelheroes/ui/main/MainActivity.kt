package com.fduranortega.marvelheroes.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fduranortega.marvelheroes.databinding.ActivityMainBinding
import com.fduranortega.marvelheroes.ui.main.adapter.HeroAdapter
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @get:VisibleForTesting
    internal val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter = HeroAdapter()
    private lateinit var paginator: RecyclerViewPaginator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initUiStateCollect()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(
            this,
            2,
            RecyclerView.VERTICAL,
            false
        )

        paginator = RecyclerViewPaginator(
            recyclerView = binding.recyclerView,
            isLoading = { viewModel.uiState.value.isLoading },
            loadMore = { viewModel.fetchMoreHeroes() },
            onLast = { false }
        )

        viewModel.fetchMoreHeroes()
    }

    private fun initUiStateCollect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .collect {
                        processUiState(it)
                    }
            }
        }
    }

    private fun processUiState(uiState: MainUiState) {
        binding.loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
        binding.emptyLabel.visibility = if (uiState.heroList.isEmpty()) View.VISIBLE else View.GONE
        when {
            uiState.errorMessage.isNotBlank() -> {}
            uiState.heroList.isNotEmpty() -> {
                adapter.setData(uiState.heroList)
            }
        }
    }
}