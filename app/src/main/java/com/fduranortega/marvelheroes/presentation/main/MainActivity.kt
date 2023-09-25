package com.fduranortega.marvelheroes.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fduranortega.marvelheroes.databinding.ActivityMainBinding
import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.presentation.main.adapter.HeroAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    internal val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter = HeroAdapter()
    private lateinit var paginator: RecyclerViewPaginator

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        observeStates()
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
            isLoading = { viewModel.viewState.value is MainViewState.ShowLoading },
            loadMore = { viewModel.dispatch(MainEvent.FetchMoreHeroes) },
            onLast = { false }
        )

        viewModel.dispatch(MainEvent.FetchMoreHeroes)
    }

    private fun observeStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    onStateChanged(it)
                }
            }
        }
    }

    private fun onStateChanged(viewState: MainViewState) {
        when (viewState) {
            MainViewState.ShowLoading -> showLoading(true)
            is MainViewState.ShowHeroList -> showHeroList(viewState.heroList)
            MainViewState.ShowEmptyList -> showEmptyList()
            is MainViewState.ShowError -> showError(viewState.message)
        }
    }

    private fun showLoading(visible: Boolean) {
        binding.loading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showHeroList(heroList: List<HeroBO>) {
        showLoading(false)
        adapter.submitList(heroList)
        binding.emptyLabel.visibility = View.GONE
    }

    private fun showEmptyList() {
        showLoading(false)
        binding.emptyLabel.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        showLoading(false)
        Snackbar.make(
            findViewById(binding.root.id),
            errorMessage,
            BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }
}