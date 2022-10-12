package com.dag.moviestore.base.compose.bottomnavigation


import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dag.moviestore.base.compose.navcontroller.NavScreen
import com.dag.moviestore.MainActivity
import com.dag.moviestore.ext.findActivity


@Composable
fun CustomBottomNavigation(
    currentRoute: String,
    navController:NavHostController
) {
    val context = LocalContext.current
    Box(
    ) {
        NavigationBar(
            modifier = Modifier
                .height(40.dp)
                .align(Alignment.BottomCenter),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(50.dp))
                BarItem.values().forEach {
                    NavigationBarItem(
                        selected = currentRoute == it.route,
                        onClick = {
                            if (currentRoute != it.route) {
                                navController.navigate(it.route) {
                                    NavScreen.HomeScreen.route.let {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = it.icon),
                                contentDescription = ""
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.size(60.dp))
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    bottom = 20.dp,
                    end = 20.dp
                ),
            onClick = {
                val activity = context.findActivity()
                activity?.apply {
                    startActivity(
                        Intent(
                            context,
                            MainActivity::class.java
                        )
                    )
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Composable
@Preview
fun BottomNavigationPreview(){
    CustomBottomNavigation(
        currentRoute = NavScreen.HomeScreen.route,
        navController = rememberNavController()
    )
}


