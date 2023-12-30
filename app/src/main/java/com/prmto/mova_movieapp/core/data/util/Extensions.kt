package com.prmto.mova_movieapp.core.data.util

import com.prmto.mova_movieapp.core.domain.models.Category
import com.prmto.mova_movieapp.core.domain.models.Sort
import com.prmto.mova_movieapp.core.domain.models.isLatestRelease
import com.prmto.mova_movieapp.core.domain.models.isMovie
import com.prmto.mova_movieapp.core.domain.models.isPopularity

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