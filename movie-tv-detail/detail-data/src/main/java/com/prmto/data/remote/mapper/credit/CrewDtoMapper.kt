package com.prmto.data.remote.mapper.credit

import com.prmto.core_data.util.orZero
import com.prmto.data.remote.dto.credit.CrewDto
import com.prmto.domain.models.credit.Crew

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