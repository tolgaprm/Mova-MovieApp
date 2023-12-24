package com.prmto.mova_movieapp.feature_person_detail.data.remote.dto

import com.prmto.mova_movieapp.feature_person_detail.domain.model.CombinedCredits

data class CombinedCreditsDto(
    val cast: List<CastDtoForPerson>?,
    val crew: List<CrewDtoForPerson>?
)

fun CombinedCreditsDto.toCombinedCredits(): CombinedCredits {
    return CombinedCredits(
        cast = cast?.map { it.toCastForPerson() }.orEmpty(),
        crew = crew?.map { it.toCrewForPerson() }.orEmpty()
    )
}