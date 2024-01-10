package com.prmto.core_data.util

import com.google.android.gms.tasks.Task
import com.prmto.core_domain.models.Category
import com.prmto.core_domain.models.Sort
import com.prmto.core_domain.models.isLatestRelease
import com.prmto.core_domain.models.isMovie
import com.prmto.core_domain.models.isPopularity
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun List<Int>.toSeparateWithComma(): String {

    if (this.isEmpty()) {
        return ""
    }

    if (this.size == 1) {
        return this[0].toString()
    }

    val stringBuilder = StringBuilder()

    val separator = ","

    for (item in this) {
        stringBuilder.append(item).append(separator)
    }

    stringBuilder.setLength(stringBuilder.length - separator.length)
    return stringBuilder.toString()
}

fun Sort.toDiscoveryQueryString(movieCategory: Category): String {

    val stringBuilder = StringBuilder()

    if (this.isPopularity()) {
        stringBuilder.append(this.value)
        return stringBuilder.append(".desc").toString()
    }

    if (this.isLatestRelease() && movieCategory.isMovie()) {
        stringBuilder.append(this.value)
    } else {
        stringBuilder.append(Constants.DISCOVER_DATE_QUERY_FOR_TV)
    }

    return stringBuilder.append(".desc").toString()
}

fun <T> Task<Void>.returnResourceByTaskResult(successItem: T): Resource<T> {
    return if (isSuccessful) {
        Resource.Success(successItem)
    } else {
        Resource.Error(UiText.unknownError())
    }
}