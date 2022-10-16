package com.dag.moviestore.base.compose.bottomnavigation

import com.dag.moviestore.base.compose.navcontroller.NavScreen
import com.dag.moviestore.R

enum class BarItem(var title:String,var icon:Int, var route:String){
    HOME_SCREEN("Home Screen", R.drawable.ic_baseline_house, NavScreen.MovieScreen.route),
    PROFILE_SCREEN("List",R.drawable.ic_baseline_map, NavScreen.HomeListScreen.route),
}
