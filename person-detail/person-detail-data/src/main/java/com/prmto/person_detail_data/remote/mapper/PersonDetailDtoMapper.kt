package com.prmto.person_detail_data.remote.mapper

import com.prmto.core_data.util.orZero
import com.prmto.person_detail_data.remote.dto.PersonDetailDto
import com.prmto.person_detail_domain.model.PersonDetail

fun PersonDetailDto.toPersonDetail(): PersonDetail {
    return PersonDetail(
        id = id.orZero(),
        name = name.orEmpty(),
        biography = biography.orEmpty(),
        birthday = birthday.orEmpty(),
        combinedCredits = combinedCreditsDto?.toCombinedCredits(),
        deathday = deathday,
        imdbId = imdbId.orEmpty(),
        knownForDepartment = knownForDepartment.orEmpty(),
        placeOfBirth = placeOfBirth.orEmpty(),
        profilePath = profilePath
    )
}