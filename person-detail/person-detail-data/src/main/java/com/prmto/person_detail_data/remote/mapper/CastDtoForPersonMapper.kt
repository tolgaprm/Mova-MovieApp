package com.prmto.person_detail_data.remote.mapper

import com.prmto.core_data.util.orZero
import com.prmto.person_detail_data.remote.dto.CastDtoForPerson
import com.prmto.person_detail_domain.model.CastForPerson

fun CastDtoForPerson.toCastForPerson(): CastForPerson {
    return CastForPerson(
        id = id.orZero(),
        name = name,
        originalName = originalName,
        originalTitle = originalTitle,
        character = character.orEmpty(),
        firstAirDate = firstAirDate,
        mediaType = mediaType.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        popularity = popularity.orZero(),
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero()
    )
}