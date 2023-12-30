package com.prmto.mova_movieapp.core.data.util

import com.prmto.mova_movieapp.core.domain.util.Resource
import com.prmto.mova_movieapp.core.domain.util.UiText

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

