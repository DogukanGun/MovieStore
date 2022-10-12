package com.dag.moviestore.dailogbox

import com.dag.moviestore.base.ui.MovieStoreDialogBox
import com.dag.moviestore.base.ui.MovieStoreViewState


interface ModelDialog: MovieStoreViewState {

    val titleRes:Int?
    val messageRes:Int?
    val negativeButton: ModelDialogButton?
    val positiveButton: ModelDialogButton
    val isCancelable:Boolean
    val isIconVisible: Boolean
    val dialogPrimaryColor: MovieStoreDialogBox.DialogPrimaryColor
}