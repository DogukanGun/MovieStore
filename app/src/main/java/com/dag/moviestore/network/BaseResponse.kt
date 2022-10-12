package com.dag.moviestore.network

import android.os.Parcelable
import com.dag.moviestore.dailogbox.DialogBoxModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseResponse:Parcelable {
    @SerializedName("Response")
    var error:Boolean = false
    @SerializedName("Error")
    var errorMessage:String = ""
    val isFailure get() = error
}