package com.prmto.person_detail_data.remote.mapper

import com.prmto.core_data.util.orZero
import com.prmto.person_detail_data.remote.dto.CrewDtoForPerson
import com.prmto.person_detail_domain.model.CrewForPerson

fun CrewDtoForPerson.toCrewForPerson(): CrewForPerson {
    return CrewForPerson(
        id = id.orZero(),
        department = department.orEmpty(),
        firstAirDate = firstAirDate,
        job = job.orEmpty(),
        mediaType = mediaType.orEmpty(),
        name = name,
        originalName = originalName,
        originalTitle = originalTitle,
        overview = overview.orEmpty(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero(),
        popularity = popularity.orZero(),
        title = title
    )
}