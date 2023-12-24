package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.detail.tv

import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.tv.CreatedByDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.CreatedBy

fun List<CreatedByDto>.toListOfCreatedBy(): List<CreatedBy> {
    return map {
        CreatedBy(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }
}