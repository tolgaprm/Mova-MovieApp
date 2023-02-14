package com.prmto.mova_movieapp.core.presentation.util

import android.app.AlertDialog
import android.content.Context
import com.prmto.mova_movieapp.R

class AlertDialogUtil {
    companion object {
        fun showAlertDialog(
            context: Context,
            onClickPositiveButton: () -> Unit
        ) {
            AlertDialog.Builder(context)
                .setTitle(R.string.sign_in)
                .setMessage(R.string.must_login_able_to_add_in_list)
                .setPositiveButton(R.string.sign_in) { _, _ ->
                    onClickPositiveButton()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }
}