package com.dag.moviestore.ext

import android.content.Context
import android.net.ConnectivityManager
import java.lang.Exception
import java.net.NetworkInterface
import java.util.*

fun Context.isNetworkStatusAvailable():Boolean{
    val connectivityManager = this
        .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val netInfo = it.activeNetworkInfo
        netInfo?.let {
            if (netInfo.isConnected) return true
        }
    }
    return false
}

fun getIPAddress(useIPv4:Boolean):String{
    try {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (interfaceItem in interfaces){
            val inetAddress = Collections.list(interfaceItem.inetAddresses)
            for (addressItem in inetAddress){
                if (!addressItem.isLoopbackAddress){
                    val hostAddress = addressItem.hostAddress
                    val isIPv4 = hostAddress.indexOf(":")<4
                    when{
                        useIPv4 -> if (isIPv4) return hostAddress
                        else -> if (!isIPv4) return extractIpAddress(hostAddress)
                    }
                }
            }
        }
    }catch (e:Exception){ }
    return ""
}

private fun extractIpAddress(sAddr:String):String{
    val delimeter = sAddr.indexOf('%')
    return if (delimeter<0){
        sAddr.toUpperCase(Locale.ROOT)
    }else{
        sAddr.substring(0,delimeter).toUpperCase(Locale.ROOT)
    }
}