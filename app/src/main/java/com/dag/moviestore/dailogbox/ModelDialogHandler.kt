package com.dag.moviestore.dailogbox

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.FragmentActivity
import com.dag.moviestore.base.general.ResourceManager
import com.dag.moviestore.base.ui.MovieStoreActivity
import com.dag.moviestore.base.ui.MovieStoreDialogBox
import javax.inject.Inject

class ModelDialogHandler @Inject constructor(
    private var resourceManager: ResourceManager
){

    fun createDialog(activity:ComponentActivity,dialog: ModelDialog):AppCompatDialog?{
        val alertDialog = MovieStoreDialogBox.showAlertDialog(
            activity = activity,
            title = resourceManager.getString(dialog.titleRes!!),
            message = resourceManager.getString(dialog.messageRes!!),
            positiveButtonTitle = resourceManager.getString(dialog.positiveButton.textRes),
            negativeButtonTitle = dialog.negativeButton?.textRes?.let { resourceManager.getString(it) },
            buttonClickListener = object : MovieStoreDialogBox.ButtonClickListener(){
                override fun onPositiveButtonClicked() {
                    super.onPositiveButtonClicked()
                    dialog.positiveButton.onClick?.invoke()

                    if (dialog is RepeatableModelDialog){
                        dialog.onRepeatAction?.invoke(dialog.repeatOnPositiveButtonClick)
                    }
                }

                override fun onNegativeButtonClicked() {
                    super.onNegativeButtonClicked()
                    dialog.negativeButton?.onClick?.invoke()

                    if (dialog is RepeatableModelDialog){
                        dialog.onRepeatAction?.invoke(dialog.repeatOnNegativeButtonClick)
                    }
                }
            },
            dialogPrimaryColor = MovieStoreDialogBox.DialogPrimaryColor.Cyan,
            isCancelable = dialog.isCancelable,
            isIconVisible = dialog.isIconVisible,
            create = true,
            dialogKey = "ModelDialogHandler"
        )
        if (alertDialog != null
            && dialog.isCancelable
            && dialog is RepeatableModelDialog
        ){
            alertDialog.setOnCancelListener { dialog.onRepeatAction?.invoke(dialog.repeatOnCancel) }
        }
        return alertDialog
    }
    fun showDialog(activity: ComponentActivity,dialog: ModelDialog){
        createDialog(activity,dialog)?.show()
    }
    private fun FragmentActivity.stringValue(
        @StringRes res:Int?
    ) = if (res!=null && res!=0) getString(res) else null
}