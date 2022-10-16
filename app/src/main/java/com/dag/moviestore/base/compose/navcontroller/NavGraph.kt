package com.dag.moviestore.base.compose.navcontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dag.moviestore.base.compose.MovieStoreSurface
import com.dag.moviestore.base.compose.appbar.CustomAppbar
import com.dag.moviestore.base.compose.bottomnavigation.CustomBottomNavigation
import com.dag.moviestore.base.general.BaseDialogBoxUtil
import com.dag.moviestore.ui.mainscreen.movies.features.MovieScreen
import com.dag.moviestore.ui.mainscreen.movies.features.MovieVM
import com.dag.moviestore.ui.onboard.login.feature.LoginScreen
import com.dag.moviestore.ui.onboard.login.feature.LoginVM
import com.dag.moviestore.ui.onboard.splash.SplashScreen
import com.dag.moviestore.ui.onboard.splash.SplashVM

@Composable
fun NavGraph(
    startDestination: String = NavScreen.SplashScreen.route,
    isOnboard: Boolean = false,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MovieStoreSurface(
        appbar = {
            if (!isOnboard) {
                CustomAppbar()
            }
        },
        bottomBar = {
            if (!isOnboard) {
                CustomBottomNavigation(
                    currentRoute = currentRoute ?: "",
                    navController = navController
                )
            }
        },
        backgroundColor = Color.LightGray
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavScreen.SplashScreen.route) {
                val viewModel = hiltViewModel<SplashVM>()
                SplashScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(NavScreen.LoginScreen.route) {
                val viewModel = hiltViewModel<LoginVM>()
                LoginScreen(
                    viewModel = viewModel,
                )
            }

            composable(NavScreen.MovieScreen.route) {
                val viewModel = hiltViewModel<MovieVM>()
                MovieScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            /*
            composable(
                NavScreen.PasswordScreen.route
                    .plus("/{username}")
                    .plus("/{userType}"),
                listOf(
                    navArgument("username") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("userType") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                )
            ) { backStackEntry ->
                val viewModel = hiltViewModel<PasswordVM>()
                val username = backStackEntry.arguments?.getString("username") ?: ""
                val userType = backStackEntry.arguments?.getString("userType") ?: ""
                PasswordScreen(
                    viewModel = viewModel,
                    userInfo = UserRegisterFirstStepInfo(
                        username = username,
                        userType = userType
                    )
                )
            }*/

        }
    }
}

fun NavHostController.navigateAndReplaceStartRoute(newHomeRoute: String,popStackRoute:String) {
    popBackStack(popStackRoute, true)
    graph.setStartDestination(newHomeRoute)
    navigate(newHomeRoute)
}