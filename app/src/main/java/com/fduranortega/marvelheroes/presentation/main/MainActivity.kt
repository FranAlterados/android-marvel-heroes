package com.fduranortega.marvelheroes.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.fduranortega.marvelheroes.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel) {
                DetailActivity.startActivity(this, it)
            }
        }
        viewModel.dispatch(MainEvent.FetchMoreHeroes)
    }

    override fun onResume() {
        super.onResume()
        viewModel.dispatch(MainEvent.OnResume)
    }
}