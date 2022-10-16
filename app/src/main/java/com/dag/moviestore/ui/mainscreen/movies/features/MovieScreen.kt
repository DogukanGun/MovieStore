package com.dag.moviestore.ui.mainscreen.movies.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dag.moviestore.base.compose.searchbar.SearchBar
import com.dag.moviestore.ui.mainscreen.HomeSurface

@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MovieVM = viewModel()
) {
    val movieState = viewModel.movieState
    HomeSurface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchBar(
                onTextChanged = { },
                modifier = Modifier
            )
            LazyColumn(

            ) {
                items(
                    movieState.value.movies.value.size,
                ){ index ->
                    Text(text = movieState.value.movies.value[index].title)
                }
            }
        }
    }
}


@Preview
@Composable
fun MovieScreenPreview() {
    MovieScreen(
        navController = rememberNavController()
    )
}