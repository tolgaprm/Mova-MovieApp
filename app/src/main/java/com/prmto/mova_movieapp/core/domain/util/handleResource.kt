package com.prmto.mova_movieapp.core.domain.util

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource

inline fun <T> handleResource(
    resourceSupplier: () -> Resource<T>,
    mapper: (T) -> T = { it }
): Resource<T> {
    return when (val resource = resourceSupplier()) {
        is Resource.Success -> {
            resource.data?.let {
                val result = mapper(it)
                Resource.Success(result)
            } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
        }

        is Resource.Error -> {
            Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
        }
    }
}