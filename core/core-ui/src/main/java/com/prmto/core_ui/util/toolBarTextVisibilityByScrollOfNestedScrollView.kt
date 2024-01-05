package com.prmto.core_ui.util

import android.content.Context
import android.graphics.Color
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.prmto.core_ui.R
import com.prmto.navigation.R as NavigationR

fun toolBarTextVisibilityByScrollPositionOfNestedScrollView(
    nestedScrollView: NestedScrollView,
    toolBarTitle: TextView,
    toolbar: Toolbar,
    position: Int,
    context: Context
) {
    nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
        if (scrollY >= position && !toolBarTitle.isVisible) {
            val isNightMode =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            if (isNightMode) {
                toolbar.setBackgroundResource(R.color.surface)
            } else {
                toolbar.setBackgroundResource(R.color.light_gray_no_alpha)
            }
            toolBarTitle.isVisible = true
            toolbar.animation = AnimationUtils.loadAnimation(context, NavigationR.anim.alpha_in)
        } else if (scrollY < position && toolBarTitle.isVisible) {
            toolbar.setBackgroundColor(Color.argb(0, 255, 255, 255))
            toolBarTitle.isVisible = false
            toolbar.animation = AnimationUtils.loadAnimation(context, NavigationR.anim.alpha_out)
        }
    }
}