package com.prmto.mova_movieapp.core.presentation.util

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity

fun addOnBackPressedCallback(
    activity: FragmentActivity,
    onBackPressed: () -> Unit
) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }
    activity.onBackPressedDispatcher.addCallback(callback)
}