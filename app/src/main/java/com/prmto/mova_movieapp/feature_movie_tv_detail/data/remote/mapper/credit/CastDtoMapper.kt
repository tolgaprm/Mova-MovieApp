package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.credit

import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.credit.CastDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Cast

fun List<CastDto>.toCast(): List<Cast> {
    return map {
        Cast(
            id = it.id.orZero(),
            originalName = it.originalName.orEmpty(),
            name = it.name.orEmpty(),
            profilePath = it.profilePath,
            character = it.character.orEmpty()
        )
    }
}