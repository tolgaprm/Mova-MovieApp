package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.credit

import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.credit.CrewDto
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