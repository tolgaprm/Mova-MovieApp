package com.prmto.mova_movieapp.core.util

import android.content.Context
import android.telephony.TelephonyManager
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun Context.getCountryIsoCode(): String {
    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return telephonyManager.simCountryIso
}


fun CombinedLoadStates.isErrorWithLoadState(): LoadState.Error? {
    return when {
        this.source.refresh is LoadState.Error -> this.source.refresh as LoadState.Error
        this.refresh is LoadState.Error -> this.refresh as LoadState.Error
        this.append is LoadState.Error -> this.append as LoadState.Error
        this.prepend is LoadState.Error -> this.prepend as LoadState.Error
        else -> null
    }
}

fun CombinedLoadStates.isLoading(): Boolean {

    return when (this.refresh) {
        is LoadState.Loading -> true
        is LoadState.NotLoading -> false
        else -> false
    }
}