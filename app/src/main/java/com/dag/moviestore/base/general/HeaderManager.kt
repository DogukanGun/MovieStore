package com.dag.moviestore.base.general

import javax.inject.Inject

class HeaderManager @Inject constructor() {
    val headers = hashMapOf<String, String>()

    fun putHeader(key: String, value: String) {
        headers[key] = value
    }

    fun removeHeader(key: String) {
        headers.remove(key)
    }
}