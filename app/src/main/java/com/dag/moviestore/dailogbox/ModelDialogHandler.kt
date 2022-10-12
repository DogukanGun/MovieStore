package com.dag.moviestore.dailogbox

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.FragmentActivity
import com.dag.moviestore.base.ui.MovieStoreDialogBox
import javax.inject.Inject

class ModelDialogHandler @Inject constructor(){

    fun createDialog(activity:FragmentActivity,dialog: ModelDialog):AppCompatDialog?{
        val alertDialog = MovieStoreDialogBox.showAlertDialog(
            activity = activity,
            title = activity.stringValue(dialog.titleRes)!!,
            message = activity.stringValue(dialog.messageRes)!!,
            positiveButtonTitle = activity.stringValue(dialog.positiveButton.textRes),
            negativeButtonTitle = activity.stringValue(dialog.negativeButton?.textRes),
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
    fun showDialog(activity: FragmentActivity,dialog: ModelDialog){
        createDialog(activity,dialog)?.show()
    }
    private fun FragmentActivity.stringValue(
        @StringRes res:Int?
    ) = if (res!=null && res!=0) getString(res) else null
}