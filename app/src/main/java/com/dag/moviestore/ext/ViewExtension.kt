package com.dag.moviestore.ext

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter



fun View.makeVisible(){
    this.visibility = View.VISIBLE
}
fun View.makeGone(){
    this.visibility = View.GONE
}
@BindingAdapter("android:background")
fun View.setBackground(res:Int?){
    background = if (res == null || res == 0){
        null
    }else {
        ContextCompat.getDrawable(context,res)
    }
}