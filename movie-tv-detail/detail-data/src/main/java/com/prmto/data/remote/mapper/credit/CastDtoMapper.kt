package com.prmto.data.remote.mapper.credit

import com.prmto.core_data.util.orZero
import com.prmto.data.remote.dto.credit.CastDto
import com.prmto.domain.models.credit.Cast

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