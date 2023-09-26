package com.fduranortega.marvelheroes.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.fduranortega.marvelheroes.domain.model.HeroBO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    companion object {
        private const val HERO_LABEL = "HERO_EXTRA"
        fun startActivity(context: Context, hero: HeroBO) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(HERO_LABEL, hero)
            context.startActivity(intent)
        }
    }

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DetailScreen(viewModel)
        }

        intent.extras?.getParcelable<HeroBO>(HERO_LABEL)?.let {
            viewModel.dispatch(DetailEvent.FetchHero(it))
        }
    }
}