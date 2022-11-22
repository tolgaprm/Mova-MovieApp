package com.prmto.mova_movieapp.util

import androidx.annotation.StringRes

sealed class Resource<T>(val data: T? = null, @StringRes val errorRes: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(@StringRes errorRes: Int?, data: T? = null) :
        Resource<T>(data = data, errorRes = errorRes)

    class Loading<T>(data: T? = null) : Resource<T>(data = data)
}