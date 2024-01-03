package com.prmto.data.remote.mapper.detail.tv

import com.prmto.core_data.util.orZero
import com.prmto.data.remote.dto.detail.tv.CreatedByDto
import com.prmto.domain.models.detail.tv.CreatedBy

fun List<CreatedByDto>.toListOfCreatedBy(): List<CreatedBy> {
    return map {
        CreatedBy(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }
}