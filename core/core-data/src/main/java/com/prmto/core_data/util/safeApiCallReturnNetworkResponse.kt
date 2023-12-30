package com.prmto.core_data.util

import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText

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

