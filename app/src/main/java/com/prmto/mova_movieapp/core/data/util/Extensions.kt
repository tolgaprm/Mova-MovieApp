package com.prmto.mova_movieapp.core.data.util

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun Boolean?.orFalse(): Boolean = this ?: false