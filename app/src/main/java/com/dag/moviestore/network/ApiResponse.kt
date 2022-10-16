package com.dag.moviestore.network

import android.os.Parcelable
import com.dag.moviestore.dailogbox.DialogBoxModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ApiResponse<T>(

    val data:T?,
    @SerializedName("dialogBoxDto")
    val dialogBox: DialogBoxModel?,
    val error:Boolean?
){
    val isFailure get() = error == true
}

@Parcelize
data class ClError(
    val message:String,
    val success:Boolean
):Parcelable

fun <T> BaseResult<ApiResponse<T>>.getDataAsResult(): BaseResult<T> {
    return when(this){
        is BaseResult.Success ->{
            val response = data ?: return BaseResult.Success(null)
            when{
                response.isFailure.not() -> BaseResult.Success(response.data)
                else -> BaseResult.Error(DialogFailure(response.dialogBox))
            }
        }
        is BaseResult.Loading -> BaseResult.Loading
        is BaseResult.Error -> BaseResult.Error(exception)
    }
}

fun <T> BaseResult<T>.getDataAsResultBaseResult(): BaseResult<T> {
    return when(this){
        is BaseResult.Success ->{
            val response = data ?: return BaseResult.Success(null)
            return BaseResult.Success(response)
        }
        is BaseResult.Loading -> BaseResult.Loading
        is BaseResult.Error -> BaseResult.Error(exception)
    }
}