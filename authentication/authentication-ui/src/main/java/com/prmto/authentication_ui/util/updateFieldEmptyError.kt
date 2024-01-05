package com.prmto.authentication_ui.util

import com.google.android.material.textfield.TextInputLayout
import com.prmto.authentication_domain.util.AuthError
import com.prmto.core_domain.R as CoreDomain

fun TextInputLayout.updateFieldEmptyError(
    authError: AuthError?
) {
    isErrorEnabled = authError != null
    when (authError) {
        is AuthError.FieldEmpty -> {
            error = this.context.getString(CoreDomain.string.error_field_empty)
        }

        null -> return
    }
}