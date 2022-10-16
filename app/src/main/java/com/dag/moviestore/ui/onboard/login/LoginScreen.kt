package com.dag.moviestore.ui.onboard.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dag.moviestore.R
import com.dag.moviestore.base.compose.roundedtextfield.RoundedTextField
import com.dag.moviestore.base.ui.MovieStoreViewModel
import com.dag.moviestore.ui.onboard.OnboardSurface
import com.dag.moviestore.ui.theme.MovieStoreTheme
import java.util.*


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginVM = viewModel()
) {
    var tabIndex = remember { mutableStateOf(0) }
    OnboardSurface {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(30.dp),
                text = stringResource(id = R.string.login_screen_tablayout_text),
                style = MaterialTheme.typography.titleLarge
                    .copy(color = MaterialTheme.colorScheme.primary)
            )
            TabLayout(
                modifier = Modifier
                    .padding(
                        start = 30.dp,
                        end = 30.dp,
                        bottom = 30.dp
                    ),
            ) {
                tabIndex.value = it
            }
            LoginInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                loginType = LoginInputType
                    .values()
                    .toList()
                    .firstOrNull {
                        it.id == tabIndex.value
                    } ?: LoginInputType.EMAIL
            )

        }
    }
}

@Composable
fun TabLayout(
    modifier: Modifier,
    tabIndexSelected: (Int) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabData = LoginInputType.values().toList().map { it.name }
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabIndex
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(
                modifier = Modifier
                    .background(Color.White),
                selected = tabIndex == index,
                onClick = {
                    tabIndex = index
                    tabIndexSelected(index)
                }, text = {
                    Text(text = text)
                })
        }
    }
}

@Composable
fun LoginInput(
    modifier: Modifier,
    loginType: LoginInputType
) {
    RoundedTextField(
        modifier = modifier,
        hint = loginType.name.replaceFirstChar { it.uppercase() },
        keyboardType = loginType.keyboardType
    )
}

@Preview(showBackground = true)
@Composable
fun LoginActivityPreview() {
    MovieStoreTheme {
        LoginScreen(
            navController = rememberNavController(),
        )
    }
}
