package com.dag.moviestore.session

import javax.inject.Inject


class MovieStoreSessionManager @Inject constructor(){
    private val sessionList:HashMap<String,Any> = HashMap()

    fun addData(key:String,data:Any) = sessionList.put(key,data)

    fun <T> getData(key: String):T? = sessionList[key] as? T

    fun contains(key: String) = sessionList[key] != null

}