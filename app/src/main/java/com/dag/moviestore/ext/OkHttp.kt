package com.dag.moviestore.ext

import okhttp3.Request

fun Request.Builder.authorizationHeader(token:String?):Request.Builder {
    if (token!=null){
        header("Authorization",token)
    }
    return this
}
