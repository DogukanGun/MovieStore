package com.dag.moviestore.dailogbox

import androidx.annotation.StringRes
import com.dag.moviestore.base.ui.MovieStoreDialogBox
import com.dag.moviestore.dailogbox.ModelDialog
import com.dag.moviestore.dailogbox.ModelDialogButton

data class DefaultModelDialog(
    @StringRes override val titleRes:Int? = null,
    @StringRes override val messageRes: Int? = null,
    override val negativeButton: ModelDialogButton? = null,
    override val positiveButton: ModelDialogButton,
    override val isCancelable: Boolean = true,
    override val isIconVisible: Boolean = true,
    override val dialogPrimaryColor: MovieStoreDialogBox.DialogPrimaryColor = MovieStoreDialogBox.DialogPrimaryColor.Red,
): ModelDialog