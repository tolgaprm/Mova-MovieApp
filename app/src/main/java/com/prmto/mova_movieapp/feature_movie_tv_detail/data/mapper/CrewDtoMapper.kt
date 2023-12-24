package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.core.data.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.credit.CrewDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Crew

fun List<CrewDto>.toCrew(): List<Crew> {
    return map {
        Crew(
            id = it.id.orZero(),
            name = it.name.orEmpty(),
            originalName = it.originalName.orEmpty(),
            profilePath = it.profilePath,
            department = it.department.orEmpty()
        )
    }
}