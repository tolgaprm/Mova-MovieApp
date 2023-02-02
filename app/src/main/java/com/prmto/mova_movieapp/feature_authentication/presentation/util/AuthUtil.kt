package com.prmto.mova_movieapp.feature_authentication.presentation.util

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.feature_authentication.util.AuthError

class AuthUtil {
    companion object {
        fun updateFieldEmptyErrorInTextInputLayout(
            textInputLayout: TextInputLayout,
            context: Context,
            authError: AuthError?
        ) {
            textInputLayout.isErrorEnabled = authError != null
            when (authError) {
                is AuthError.FieldEmpty -> {
                    textInputLayout.error = context.getString(R.string.error_field_empty)
                }
                null -> return
            }
        }
    }
}