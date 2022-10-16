package com.dag.moviestore.ui.onboard.login

import androidx.compose.ui.text.input.KeyboardType

enum class LoginInputType(val id:Int,val keyboardType: KeyboardType) {
    EMAIL(0, KeyboardType.Email),
    PHONE(1, KeyboardType.Phone)
}