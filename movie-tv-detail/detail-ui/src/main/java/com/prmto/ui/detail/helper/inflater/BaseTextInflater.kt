package com.prmto.ui.detail.helper.inflater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.textview.MaterialTextView
import com.prmto.ui.R

abstract class BaseTextInflater(
    private val context: Context,
    private val viewGroup: ViewGroup
) {
    protected fun createText(
        name: String,
        id: Int
    ): MaterialTextView {
        val creatorText = LayoutInflater.from(context)
            .inflate(
                R.layout.creator_text,
                viewGroup,
                false
            ) as MaterialTextView
        creatorText.text = name
        creatorText.id = id
        return creatorText
    }
}