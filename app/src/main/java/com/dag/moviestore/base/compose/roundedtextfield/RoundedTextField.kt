package com.dag.moviestore.base.compose.roundedtextfield

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedTextField(
    modifier: Modifier,
    hint:String,
    keyboardType: KeyboardType
){
    val text = remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        modifier = modifier,
        value = text.value,
        shape = RoundedCornerShape(size = 70.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = {
            text.value = it
        },
        placeholder = {
            Text(text = hint)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview
@Composable
fun RoundedTextFieldPreview(){
    RoundedTextField(
        modifier = Modifier,
        hint = "Email",
        keyboardType = KeyboardType.Text
    )
}