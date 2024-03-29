package com.prmto.core_domain.util

import com.google.firebase.firestore.FirebaseFirestoreException
import com.prmto.core_domain.R
import timber.log.Timber

class FirebaseFirestoreErrorMessage {

    companion object {

        fun getMessage(
            errorCode: String
        ): Int {
            var errorStringId = R.string.unknown_error
            errorMessages.forEach { (t, u) ->
                if (errorCode == t) {
                    errorStringId = u
                }
            }
            return errorStringId
        }

        private val errorMessages = mapOf(
            "PERMISSION_DENIED" to R.string.permission_denied_error,
            "NOT_FOUND" to R.string.not_found_error,
            "ALREADY_EXISTS" to R.string.already_exists_error,
            "ABORTED" to R.string.aborted_error,
            "UNAVAILABLE" to R.string.unavailable_error
        )

        fun <T> setExceptionToFirebaseMessage(
            exception: Exception
        ): Resource<T> {
            Timber.e(exception.localizedMessage?.toString())
            return if (exception is FirebaseFirestoreException) {
                val errorCode = exception.code.toString()
                val errorStringId = getMessage(errorCode = errorCode)
                Resource.Error(UiText.StringResource(errorStringId))
            } else {
                Resource.Error(UiText.StringResource(R.string.unknown_error))
            }
        }
    }
}