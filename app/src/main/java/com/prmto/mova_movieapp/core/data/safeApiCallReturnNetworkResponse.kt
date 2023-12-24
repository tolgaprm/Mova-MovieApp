package com.prmto.mova_movieapp.core.data

import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource

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

