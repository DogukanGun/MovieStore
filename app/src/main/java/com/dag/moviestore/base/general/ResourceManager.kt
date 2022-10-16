package com.dag.moviestore.base.general

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getString(@StringRes stringId: Int, params: List<String> = listOf()): String {
        var str = context.getString(stringId)
        for (index in 0.until(params.size)) {
            str = str.replace("{${index + 1}}", params[index])
        }
        return str
    }

    fun getString(@StringRes stringId: Int):String{
        return context.getString(stringId)
    }
}