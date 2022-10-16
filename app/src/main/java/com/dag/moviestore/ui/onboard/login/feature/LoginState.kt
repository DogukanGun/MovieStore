package com.dag.moviestore.ui.onboard.login.feature

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LoginState(
    val errorMessage:MutableState<String> = mutableStateOf(""),
    val loginCompleted:MutableState<Boolean> = mutableStateOf(false),
    val showPinPage:MutableState<Boolean> = mutableStateOf(false)
)
