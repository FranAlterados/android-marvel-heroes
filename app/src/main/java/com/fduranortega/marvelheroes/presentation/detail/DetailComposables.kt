package com.fduranortega.marvelheroes.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fduranortega.marvelheroes.domain.model.HeroBO
import com.fduranortega.marvelheroes.domain.model.HeroExtraBO
import com.fduranortega.marvelheroes.presentation.main.ErrorScreen
import com.fduranortega.marvelheroes.presentation.main.LoadingComponent

@Composable
fun DetailScreen(viewModel: DetailViewModel) {
    val viewState by viewModel.viewState.collectAsState()
    when (viewState) {
        is DetailViewState.ShowError -> ErrorScreen((viewState as DetailViewState.ShowError).message)
        is DetailViewState.ShowHero -> HeroScreen(
            (viewState as DetailViewState.ShowHero).loading,
            (viewState as DetailViewState.ShowHero).heroBO
        )
    }
}

@Composable
fun HeroScreen(loading: Boolean, heroBO: HeroBO?) {
    Box {
        heroBO?.let {
            Column {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                ) {
                    AsyncImage(
                        modifier = Modifier.aspectRatio(1f),
                        model = heroBO.urlImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = heroBO.name,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        text = heroBO.description,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    LazyRow {
                        items(heroBO.comics.size) {
                            ComicCard(comic = heroBO.comics[it])
                        }
                    }
                }
            }
        }

        if (loading) {
            LoadingComponent(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun ComicCard(comic: HeroExtraBO) {
    Box(
        modifier = Modifier.width(150.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f),
            model = comic.urlImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black, RectangleShape),
                text = comic.title,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
            )
        }
    }

}