package com.fduranortega.marvelheroes.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.fduranortega.marvelheroes.databinding.ActivityDetailBinding
import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.domain.model.HeroExtraBO
import com.fduranortega.marvelheroes.presentation.detail.adapter.HeroExtraInfoAdapter
import com.fduranortega.marvelheroes.presentation.detail.adapter.HeroExtraInfoAdapter.Companion.PLACEHOLDER_ID
import com.fduranortega.marvelheroes.utils.EMPTY_STRING
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationCompat.onTransformationEndContainerApplyParams
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        private const val HERO_LABEL = "HERO_EXTRA"
        fun startActivity(transformationLayout: TransformationLayout, hero: HeroBO) {
            val intent = Intent(transformationLayout.context, DetailActivity::class.java)
            intent.putExtra(HERO_LABEL, hero)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }

    @get:VisibleForTesting
    internal val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private val adapter = HeroExtraInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initUiStateCollect()
        initRecyclerView()

        intent.extras?.getParcelable<HeroBO>(HERO_LABEL)?.let {
            bindHero(it)
            viewModel.fetchHero(it.id)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun initRecyclerView() {
        binding.heroExtraInfoRecyclerView.adapter = adapter
    }

    private fun bindHero(hero: HeroBO) {
        binding.heroName.text = hero.name
        binding.heroDescription.text = hero.description
        Glide.with(binding.root.context)
            .load(hero.urlImage)
            .listener(
                GlidePalette.with(hero.urlImage)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            binding.root.setBackgroundColor(rgb)
                        }
                    }.crossfade(true)
            )
            .into(binding.heroImage)
        if (hero.comics.isEmpty()) {
            adapter.submitList(
                List(hero.numComics) {
                    HeroExtraBO(PLACEHOLDER_ID, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
                }
            )
        } else {
            adapter.submitList(hero.comics)
        }

        if (hero.numComics == 0) {
            binding.heroComicTitle.visibility = View.GONE
        }
        if (hero.description.isBlank()) {
            binding.heroDescription.visibility = View.GONE
        }
    }

    private fun initUiStateCollect() {
        lifecycleScope.launch {
            viewModel.uiState
                .collect {
                    processUiState(it)
                }
        }
    }

    private fun processUiState(uiState: DetailUiState) {
        when {
            uiState.errorMessage.isNotBlank() -> {
                showError(uiState.errorMessage)
            }
            uiState.hero != null -> {
                bindHero(uiState.hero)
            }
        }
    }

    private fun showError(errorMessage: String) {
        Snackbar.make(
            findViewById(binding.root.id),
            errorMessage,
            BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }
}