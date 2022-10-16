package com.dag.moviestore.ui.onboard.splash

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dag.moviestore.base.compose.navcontroller.NavScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashVM = viewModel()
) {
    val scope = rememberCoroutineScope()
    Surface {
        Text(text = "Welcome to Movie Store")
        scope.launch(context = Dispatchers.IO) {
            delay(1000L)
            navController.navigate(NavScreen.LoginScreen.route)
        }
    }
}