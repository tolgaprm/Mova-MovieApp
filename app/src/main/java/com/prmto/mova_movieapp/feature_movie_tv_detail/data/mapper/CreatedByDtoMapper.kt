package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.core.data.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.CreatedByDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.CreatedBy

fun List<CreatedByDto>.toListOfCreatedBy(): List<CreatedBy> {
    return map {
        CreatedBy(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }
}