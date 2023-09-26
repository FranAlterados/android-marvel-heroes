package com.fduranortega.marvelheroes.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fduranortega.marvelheroes.R
import com.fduranortega.marvelheroes.domain.model.HeroBO

@Composable
fun MainScreen(viewModel: MainViewModel, onHeroClicked: (HeroBO) -> Unit) {
    val listState = rememberLazyGridState()
    val viewState by viewModel.viewState.collectAsState()
    when (viewState) {
        MainViewState.ShowEmptyList ->
            EmptyScreen()

        is MainViewState.ShowError ->
            ErrorScreen((viewState as MainViewState.ShowError).message)

        is MainViewState.ShowHeroList ->
            HeroListScreen(
                (viewState as MainViewState.ShowHeroList).loading,
                (viewState as MainViewState.ShowHeroList).heroList,
                viewModel,
                listState
            )

        is MainViewState.OnHeroClicked ->
            onHeroClicked.invoke((viewState as MainViewState.OnHeroClicked).hero)
    }
}

@Composable
fun CenteredText(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun LoadingComponent(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
    )
}


@Composable
fun ErrorScreen(
    message: String
) {
    CenteredText(message)
}

@Composable
fun HeroListScreen(
    loading: Boolean,
    heroList: List<HeroBO>,
    viewModel: MainViewModel,
    listState: LazyGridState,
) {
    Box {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            columns = GridCells.Fixed(2)
        ) {
            items(heroList.size) {
                HeroCard(heroList[it], viewModel)
            }

        }
        listState.OnBottomReached {
            viewModel.dispatch(MainEvent.FetchMoreHeroes)
        }

        if (loading) {
            LoadingComponent(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun EmptyScreen() {
    CenteredText(stringResource(R.string.empty_label))
}

@Composable
fun LazyGridState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}

@Composable
fun HeroCard(
    hero: HeroBO,
    viewModel: MainViewModel,
) {
    Box(
        modifier = Modifier.clickable {
            viewModel.dispatch(MainEvent.OnClickHero(hero))
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
            model = hero.urlImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.Black, RectangleShape)
                .padding(6.dp, 2.dp, 6.dp, 2.dp),
            text = hero.name,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.Black, RectangleShape)
                .padding(6.dp, 2.dp, 6.dp, 2.dp),
            text = hero.numComics.toString(),
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}