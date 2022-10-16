package com.dag.moviestore.ui.mainscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dag.moviestore.base.compose.navcontroller.NavGraph
import com.dag.moviestore.base.compose.navcontroller.NavScreen
import com.dag.moviestore.ui.mainscreen.ui.theme.MovieStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(
                        startDestination = NavScreen.MovieScreen.route,
                        isOnboard = false,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeSurface(
    content:@Composable ()->Unit
){
    com.dag.moviestore.ui.theme.MovieStoreTheme() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            color = Color.Transparent
        ) {
            content()
        }
    }
}
