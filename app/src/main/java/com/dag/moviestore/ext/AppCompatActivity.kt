package com.dag.moviestore.ext

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import com.dag.moviestore.R

fun FragmentActivity.openClAppsSetting(){
    startActivity(
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package",packageName,null)
        }
    )
}

fun FragmentActivity.openActivity(
    intent: Intent,
    enterAnim:Int = R.anim.slide_in_left,
    exitAnim:Int = R.anim.slide_out_left
){
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    startActivity(intent)
    overridePendingTransition(enterAnim,exitAnim)
}


fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}