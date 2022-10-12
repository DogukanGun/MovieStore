package com.dag.moviestore.base.compose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.moviestore.ui.theme.MovieStoreTheme


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    border: BorderStroke = BorderStroke(1.dp, Color.Black),
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
){
    Button(
        modifier = modifier,
        onClick = onClick,
        border = border,
        colors = ButtonDefaults.buttonColors(contentColor = color),
        elevation = ButtonDefaults.elevatedButtonElevation(0.dp),
        shape = RoundedCornerShape(7.dp)
    ) {
        content()
    }
}

@Composable
@Preview
fun CustomButtonPreview(){
    MovieStoreTheme() {
        CustomButton(
            onClick = {},
            color = MaterialTheme.colorScheme.primary,
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(
                text = "Get Started",
                color = Color.White
            )
        }
    }
}