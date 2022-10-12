package com.dag.moviestore.base.compose.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.moviestore.base.compose.MovieStorePreview
import com.dag.moviestore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppbar() {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                )
            }
        },
        navigationIcon = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_qr_code
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

        ),
        actions = {
            /*Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_person
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color.Black
                )
            }*/
        }
    )
}

@Composable
@Preview
fun CustomAppbarPreview() {
    MovieStorePreview {
        CustomAppbar()
    }
}