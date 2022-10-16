package com.dag.moviestore.base.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dag.moviestore.base.compose.appbar.CustomAppbar
import com.dag.moviestore.base.compose.bottomnavigation.CustomBottomNavigation
import com.dag.moviestore.ui.theme.MovieStoreTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieStoreSurface(
    appbar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {

    Scaffold(
        topBar = appbar,
        bottomBar = bottomBar,
    ) {
        Surface {
            content()
        }
    }

}

@Composable
fun MovieStorePreview(content: @Composable () -> Unit){
    MovieStoreTheme {
        content()
    }
}

@Preview
@Composable
fun MovieStoreSurfacePreview() {
    MovieStoreSurface(
        appbar = {
            CustomAppbar()
        },
        bottomBar = {
        },
        backgroundColor = Color.LightGray
    ) {
        Text(text = "Deneme")
    }
}