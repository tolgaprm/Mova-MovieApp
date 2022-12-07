package com.prmto.mova_movieapp.util

import android.content.Context
import android.telephony.TelephonyManager

fun Context.getCountryIsoCode(): String {
    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return telephonyManager.simCountryIso
}