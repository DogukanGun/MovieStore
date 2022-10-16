package com.dag.moviestore.base.compose.navcontroller

sealed class NavScreen(val route:String) {

    object SplashScreen: NavScreen("splash_screen")
    object MovieScreen: NavScreen("movie_screen")
    object HomeListScreen: NavScreen("homelist_screen")
    object LoginScreen: NavScreen("login_screen")

}

fun NavScreen.addData(data:Any):String{
    return this.route.plus(data)
}

fun NavScreen.addPath(path:String):String{
    return this.route.plus("/").plus(path)
}