package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.credit.CrewDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Crew

fun List<CrewDto>.toCrew(): List<Crew> {
    return map {
        Crew(
            id = it.id,
            name = it.name,
            originalName = it.originalName,
            profilePath = it.profilePath,
            department = it.department
        )
    }
}