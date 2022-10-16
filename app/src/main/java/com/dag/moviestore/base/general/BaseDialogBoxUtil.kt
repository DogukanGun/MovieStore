package com.dag.moviestore.base.general

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.dag.moviestore.network.BaseResult
import com.dag.moviestore.R
import com.dag.moviestore.base.ui.MovieStoreActivity
import com.dag.moviestore.base.ui.MovieStoreNavigator
import com.dag.moviestore.dailogbox.*
import com.dag.moviestore.di.NetworkModule
import com.dag.moviestore.ext.tryCatch
import com.dag.moviestore.network.MovieStoreService
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.alert_dialog_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class BaseDialogBoxUtil @Inject constructor(
    private val clNavigator: MovieStoreNavigator,
    private val service: MovieStoreService,
    private val movieStoreActivityListener: MovieStoreActivityListener
){
    var baseDialogBoxCustomActionListener: BaseDialogBoxCustomActionListener? = null
    interface BaseDialogBoxCustomActionListener{
        fun baseDialogBoxCustomAction(buttonModel: ButtonModel? = null)
    }

    fun showGenericDialog(dialogBox: DialogBoxModel, activity:Activity? = movieStoreActivityListener.currentActivity){
        activity?.run {
            if (activity.isFinishing){
                return
            }
            val builder = AlertDialog.Builder(activity)
            val dialogView = activity.layoutInflater.inflate(R.layout.alert_dialog_layout,null)
            val isIconVisible = dialogBox.isIconVisible == true
            val isCancelable = dialogBox.cancelable == true
            if (isIconVisible){
                dialogView.alertDialogErrorIcon.setImageResource(R.drawable.ic_xmark_solid)
            }else{
                dialogView.alertDialogErrorIcon.visibility = View.GONE
            }
            when(dialogBox.color){
                DailogBoxColorType.ORANGE -> {
                    dialogView.alertDialogTitle.setTextColor(R.color.yellow)
                    dialogView.alertDialogNegativeButton.setBackgroundResource(R.drawable.dialog_box_button_background_orange)
                    dialogView.alertDialogNegativeButton.setTextColor(R.color.black)

                    dialogView.alertDialogPositiveButton.setBackgroundResource(R.drawable.dialog_box_button_background_orange)
                    dialogView.alertDialogPositiveButton.setTextColor(R.color.black)
                }
                DailogBoxColorType.YELLOW -> {
                    dialogView.alertDialogTitle.setTextColor(R.color.yellow)
                    dialogView.alertDialogNegativeButton.setBackgroundResource(R.drawable.dialog_box_button_background_yellow)
                    dialogView.alertDialogNegativeButton.setTextColor(R.color.black)

                    dialogView.alertDialogPositiveButton.setBackgroundResource(R.drawable.dialog_box_button_background_yellow)
                    dialogView.alertDialogPositiveButton.setTextColor(R.color.black)
                }
                DailogBoxColorType.CYAN -> {
                    dialogView.alertDialogTitle.setTextColor(R.color.cyan)
                    dialogView.alertDialogNegativeButton.setBackgroundResource(R.drawable.dialog_box_button_background_cyan)
                    dialogView.alertDialogNegativeButton.setTextColor(R.color.black)

                    dialogView.alertDialogPositiveButton.setBackgroundResource(R.drawable.dialog_box_button_background_cyan)
                    dialogView.alertDialogPositiveButton.setTextColor(R.color.black)
                }
                DailogBoxColorType.PURPLE -> {
                    dialogView.alertDialogTitle.setTextColor(R.color.purple)
                    dialogView.alertDialogNegativeButton.setBackgroundResource(R.drawable.dialog_box_button_background_purple)
                    dialogView.alertDialogNegativeButton.setTextColor(R.color.white)

                    dialogView.alertDialogPositiveButton.setBackgroundResource(R.drawable.dialog_box_button_background_purple)
                    dialogView.alertDialogPositiveButton.setTextColor(R.color.white)
                }
                DailogBoxColorType.GREEN -> {
                    dialogView.alertDialogTitle.setTextColor(R.color.green)
                    dialogView.alertDialogNegativeButton.setBackgroundResource(R.drawable.dialog_box_button_background_green)
                    dialogView.alertDialogNegativeButton.setTextColor(R.color.black)

                    dialogView.alertDialogPositiveButton.setBackgroundResource(R.drawable.dialog_box_button_background_green)
                    dialogView.alertDialogPositiveButton.setTextColor(R.color.black)
                }
                DailogBoxColorType.RED -> {
                    dialogView.alertDialogTitle.setTextColor(R.color.red)
                    dialogView.alertDialogTitle.setTextColor(R.color.green)
                    dialogView.alertDialogNegativeButton.setBackgroundResource(R.drawable.dialog_box_button_background_red)
                    dialogView.alertDialogNegativeButton.setTextColor(R.color.white)

                    dialogView.alertDialogPositiveButton.setBackgroundResource(R.drawable.dialog_box_button_background_red)
                    dialogView.alertDialogPositiveButton.setTextColor(R.color.white)
                }
                else -> {}
            }
            builder.setView(dialogView)
            builder.setCancelable(isCancelable)
            dialogView.setBackgroundColor(ContextCompat.getColor(activity.applicationContext,R.color.white))
            val alertDialog = builder.create()
            dialogView.alertDialogTitle.text = dialogBox.title
            dialogView.alertDialogMessage.text = dialogBox.message

            dialogBox.buttonList?.getOrNull(0)?.let { buttonModel->
                dialogView.alertDialogNegativeButton.text = buttonModel.text.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.US
                    ) else it.toString()
                }
                dialogView.alertDialogNegativeButton.setOnClickListener {
                    buttonAction(buttonModel,activity)
                    alertDialog.dismiss()
                }
            } ?: run {
                dialogView.alertDialogNegativeButton.visibility = View.GONE
            }

            dialogBox.buttonList?.getOrNull(1)?.let { buttonModel ->
                dialogView.alertDialogPositiveButton.text = buttonModel.text.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.US
                    ) else it.toString()
                }
                dialogView.alertDialogPositiveButton.setOnClickListener {
                    buttonAction(buttonModel,activity)
                    alertDialog.dismiss()
                }
            } ?: run {
                dialogView.alertDialogPositiveButton.visibility = View.GONE
            }

            alertDialog.window?.setBackgroundDrawable(AppCompatResources.getDrawable(activity,
                androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha))

            alertDialog.show()
        }
    }

    private fun buttonAction(buttonModel: ButtonModel?, activity: Activity?){
        when(buttonModel?.actionType){
            ButtonActionType.CUSTOM -> baseDialogBoxCustomActionListener?.baseDialogBoxCustomAction()
            ButtonActionType.GO_BACK -> tryCatch({activity?.onBackPressed()})
            ButtonActionType.DISMISS -> Unit
            ButtonActionType.REQUEST ->{
                if (buttonModel.onPressed != null){
                    buttonModel.onPressed?.invoke()
                }else{
                    sendGenericRequest(activity,buttonModel.requestModel)
                }
            }
            else -> {buttonModel?.onPressed?.invoke()}
        }
    }

    fun sendGenericRequest(activity: Activity?, requestModel: RequestModel?, onSuccess:()->Unit = {}){
        if (activity is MovieStoreActivity<*, *>) {
            var body: Any? = null
            if (!requestModel?.body.isNullOrEmpty()) {
                body = hashMapOf<String, Any>().apply {
                    requestModel?.body?.forEach {
                        if (it.key is String && it.value != null) {
                            put(it.key, it.value)
                        }
                    }
                }
            } else if (requestModel?.bodyAsJsonString?.isEmpty() == false) {
                body = JsonParser.parseString(requestModel.bodyAsJsonString).asJsonObject
            }

            val requestFunction = when(requestModel?.method){
                RequestMethodType.POST -> service.genericPostRequest(requestModel.url,body)
                RequestMethodType.GET -> service.genericGetRequest(requestModel.url)
                RequestMethodType.DELETE -> service.genericDeleteRequest(requestModel.url)
                RequestMethodType.PUT -> service.genericPutRequest(requestModel.url,body)
                else -> {null}
            }
            tryCatch(
                tryBlock ={
                    (requestFunction as? BaseResult<*>)?.run {
                        CoroutineScope(Dispatchers.IO).launch {
                            onSuccess.invoke()
                        }
                    }
                }
            )
        }
    }

}