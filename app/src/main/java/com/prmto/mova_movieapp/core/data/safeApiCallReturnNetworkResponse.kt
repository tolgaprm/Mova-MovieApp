package com.prmto.mova_movieapp.core.data

import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import retrofit2.Response

suspend inline fun <T : Any> safeApiCallReturnNetworkResponse(
    crossinline apiCall: suspend () -> Response<T>,
): Resource<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            response.body()?.let { Resource.Success(it) }
                ?: Resource.Error(UiText.somethingWentWrong())
        } else {
            Resource.Error(UiText.somethingWentWrong())
        }
    } catch (e: Exception) {
        Resource.Error(UiText.somethingWentWrong())
    }
}

