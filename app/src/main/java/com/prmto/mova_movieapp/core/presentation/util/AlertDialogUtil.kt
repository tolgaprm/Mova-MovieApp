package com.prmto.mova_movieapp.core.presentation.util

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.StringRes

class AlertDialogUtil {
    companion object {
        fun showAlertDialog(
            context: Context,
            @StringRes title: Int,
            @StringRes message: Int,
            @StringRes positiveBtnMessage: Int,
            @StringRes negativeBtnMessage: Int,
            onClickPositiveButton: () -> Unit
        ) {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnMessage) { _, _ ->
                    onClickPositiveButton()
                }
                .setNegativeButton(negativeBtnMessage) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }
}