package com.dag.moviestore.base.compose.searchbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.moviestore.R
import com.dag.moviestore.ui.theme.MovieStoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onTextChanged:(String)->Unit,
    modifier: Modifier
){
    val textFieldValue = remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(30.dp),
                color = Color.Black
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextField(
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
                onTextChanged(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.searchbar_placeholder_movies),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_search),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun SearchBarPreview(){
    MovieStoreTheme {
        SearchBar(
            modifier = Modifier
                .background(Color.White),
            onTextChanged = {}
        )
    }
}