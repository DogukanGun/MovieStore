package com.dag.moviestore.dailogbox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class DialogBoxModel(
    @SerializedName("dialogBoxType")
    val type: DialogBoxType? = null,
    @SerializedName("dialogBoxColorType")
    val color: DailogBoxColorType? = null,
    @SerializedName("isVisible")
    val isIconVisible:Boolean? = null,
    val title:String? = null,
    val message:String? = null,
    val imageUrl:String? = null,
    val buttonList:List<ButtonModel>? = null,
    val cancelable:Boolean? = null,
    val showManuel:Boolean? = null,
):Parcelable

@Parcelize
enum class DialogBoxType:Parcelable{
    INFO,
    WARNING,
    ERROR,
    BLOCKER,
    DROP_SESSION
}

@Parcelize
enum class DailogBoxColorType:Parcelable{
    @SerializedName(value = "RED")
    RED,
    @SerializedName(value = "YELLOW")
    YELLOW,
    @SerializedName(value = "GREEN")
    GREEN,
    @SerializedName(value = "PURPLE")
    PURPLE,
    @SerializedName(value = "CYAN")
    CYAN,
    @SerializedName(value = "ORANGE")
    ORANGE
}

@Parcelize
data class ButtonModel(
    val text:String,
    var actionType: ButtonActionType? = null,
    val buttonNavigationModel: NavigationModel? = null,
    val requestModel: RequestModel? = null,
    @IgnoredOnParcel var onPressed: @RawValue (()->Unit)? = null
):Parcelable

@Parcelize
data class RequestModel(
    var url:String? = null,
    var method: RequestMethodType? = null,
    val body:@RawValue List<KeyValueModel<String, Any?>>? = null,
    val bodyAsJsonString:String? = null
): Parcelable

@Parcelize
data class NavigationModel(
    val deepLinkCode:Int? = null,
    val deepLinkUrl:String? = null,
    var parameters:@RawValue List<KeyValueModel<String, Any?>>? = null
): Parcelable

data class KeyValueModel<K,V>(
    @SerializedName(value = "key")
    val key: K?,

    @SerializedName(value = "value")
    val value: V?
)

@Parcelize
data class UriNavigation(
    var uri:String? = null
):Parcelable

@Parcelize
enum class ButtonActionType:Parcelable{
    DEEP_LINK,
    NAVIGATION,
    REQUEST,
    DISMISS,
    GO_BACK,
    URI_NAVIGATION,
    CUSTOM
}

@Parcelize
enum class RequestMethodType:Parcelable{
    GET,
    POST,
    PUT,
    DELETE
}
