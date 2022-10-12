package com.dag.moviestore.dailogbox

import androidx.annotation.StringRes
import com.dag.moviestore.base.ui.MovieStoreDialogBox

data class DefaultRepeatableModelDialog(
    @StringRes override val titleRes: Int,
    @StringRes override val messageRes: Int,
    override val negativeButton: ModelDialogButton? = null,
    override val positiveButton: ModelDialogButton,
    override val isCancelable: Boolean = true,
    override val isIconVisible: Boolean = true,
    override val dialogPrimaryColor: MovieStoreDialogBox.DialogPrimaryColor = MovieStoreDialogBox.DialogPrimaryColor.Cyan,
    override val repeatOnPositiveButtonClick: Boolean = true,
    override val repeatOnNegativeButtonClick: Boolean = false,
    override val repeatOnCancel: Boolean = false,
) : RepeatableModelDialog {
    override var onRepeatAction: ((Boolean) -> Unit)? = null
}