package com.prmto.authentication_domain.util

import com.prmto.authentication_domain.R
import com.prmto.core_domain.R as CoreDomainR

class FirebaseAuthMessage {

    companion object {
        fun getMessage(
            errorCode: String
        ): Int {
            var errorStringId = CoreDomainR.string.unknown_error
            errorMessages.forEach { t, u ->
                if (errorCode == t) {
                    errorStringId = u
                }
            }
            return errorStringId
        }

        private val errorMessages = mapOf(
            "ERROR_INVALID_CUSTOM_TOKEN" to R.string.error_invalid_custom_token,
            "ERROR_CUSTOM_TOKEN_MISMATCH" to R.string.error_custom_token_mismatch,
            "ERROR_INVALID_CREDENTIAL" to R.string.error_invalid_credential,
            "ERROR_INVALID_EMAIL" to R.string.error_invalid_email,
            "ERROR_WRONG_PASSWORD" to R.string.error_wrong_password,
            "ERROR_USER_MISMATCH" to R.string.error_user_mismatch,
            "ERROR_REQUIRES_RECENT_LOGIN" to R.string.error_requires_recent_login,
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" to R.string.error_account_exists_with_different_credential,
            "ERROR_EMAIL_ALREADY_IN_USE" to R.string.error_email_already_in_use,
            "ERROR_CREDENTIAL_ALREADY_IN_USE" to R.string.error_credential_already_in_use,
            "ERROR_USER_DISABLED" to R.string.error_user_disabled,
            "ERROR_USER_TOKEN_EXPIRED" to R.string.error_user_token_expired,
            "ERROR_USER_NOT_FOUND" to R.string.error_user_not_found,
            "ERROR_INVALID_USER_TOKEN" to R.string.error_invalid_user_token,
            "ERROR_OPERATION_NOT_ALLOWED" to R.string.error_operation_not_allowed,
            "ERROR_WEAK_PASSWORD" to R.string.error_weak_password
        )
    }
}