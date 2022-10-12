package com.dag.moviestore.network

import android.content.Context
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.dag.moviestore.R

class GenericResponseHeader {

    companion object{
        private const val PROGRESS_BAR_ANIMATION_DURATION:Long = 1000

        fun<T:Any> onResponse(result:T?){}

        fun onError(error:Throwable?) {}

        fun showProgress(window:Window?,context:Context){
            window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            var progressHolder = window?.findViewById<LinearLayout>(R.id.progressBarHolder)
            if (progressHolder == null){
                progressHolder = LayoutInflater.from(context).inflate(R.layout.loading_layout,null) as LinearLayout
                val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                params.addRule(RelativeLayout.CENTER_IN_PARENT)
                window?.addContentView(progressHolder,params)
            }
            if (progressHolder.visibility != View.VISIBLE){
                progressHolder.visibility = View.VISIBLE
            }
            progressHolder.alpha = 0F
            progressHolder.animate().alpha(1F).duration = PROGRESS_BAR_ANIMATION_DURATION
        }

        fun hideProgress(window: Window?){
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            val progressBar = window?.findViewById<LinearLayout>(R.id.progressBarHolder)
            progressBar?.visibility = View.GONE
        }
    }
}