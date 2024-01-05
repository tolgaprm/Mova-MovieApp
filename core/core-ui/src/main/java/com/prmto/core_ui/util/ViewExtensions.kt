package com.prmto.core_ui.util

import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.prmto.core_ui.R

fun AdView.loadAd() {
    val adRequest = AdRequest.Builder().build()
    loadAd(adRequest)
}

fun View.makeVisible() {
    this.isVisible = true
}

fun View.makeGone() {
    this.isVisible = false
}

fun ImageButton.setAddFavoriteIconByFavoriteState(isFavorite: Boolean) {
    if (isFavorite) {
        setImageResource(R.drawable.ic_favorite_white)
    } else {
        setImageResource(R.drawable.ic_favorite_border_white)
    }
}

fun ImageButton.setWatchListIconByWatchState(isAddedWatchList: Boolean) {
    if (isAddedWatchList) {
        setImageResource(R.drawable.ic_video_white)
    } else {
        setImageResource(R.drawable.outline_video_library_24)
    }
}