package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.inflater

import android.content.Context
import android.view.ViewGroup
import com.prmto.domain.models.detail.tv.CreatedBy

class CreatorTextInflater(
    private val context: Context,
    private val viewGroup: ViewGroup
) : BaseTextInflater(context, viewGroup) {

    fun createCreatorTexts(
        createdByList: List<CreatedBy>?
    ) {
        if (createdByList.isNullOrEmpty()) {
            viewGroup.removeAllViews()
            return
        }
        createdByList.forEach { createdBy ->
            val creatorText = createText(
                name = createdBy.name,
                id = createdBy.id
            )
            viewGroup.addView(creatorText)
        }
    }
}