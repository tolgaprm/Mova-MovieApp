package com.prmto.core_data.util

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.prmto.core_domain.util.FirebaseFirestoreErrorMessage
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText
import timber.log.Timber

suspend inline fun <T : Any> safeApiCallReturnResource(
    crossinline apiCall: suspend () -> T,
): Resource<T> {
    return try {
        val response = apiCall.invoke()
        Resource.Success(response)
    } catch (e: Exception) {
        Resource.Error(UiText.somethingWentWrong())
    }
}

inline fun <T> safeCallWithForFirebase(
    setFirebaseAuthErrorMessage: (exception: FirebaseAuthException) -> Resource<T> = {
        Resource.Error(UiText.unknownError())
    },
    call: () -> Resource<T>,
): Resource<T> {
    return try {
        call()
    } catch (e: FirebaseFirestoreException) {
        Timber.d(e.localizedMessage)
        FirebaseFirestoreErrorMessage.setExceptionToFirebaseMessage(e)
    } catch (exception: FirebaseAuthException) {
        setFirebaseAuthErrorMessage(exception)
    } catch (e: Exception) {
        Timber.d(e.localizedMessage)
        Resource.Error(UiText.somethingWentWrong())
    }
}