package com.dag.moviestore.ui.onboard.login.data

import androidx.compose.ui.text.input.KeyboardType

enum class LoginInputType(
    val id: Int,
    val keyboardType: KeyboardType,
    val passwordRequired: Boolean,
) {
    EMAIL(0, KeyboardType.Email, true),
    PHONE(1, KeyboardType.Phone, false),
    PASSWORD(-1, KeyboardType.Password, false);

}