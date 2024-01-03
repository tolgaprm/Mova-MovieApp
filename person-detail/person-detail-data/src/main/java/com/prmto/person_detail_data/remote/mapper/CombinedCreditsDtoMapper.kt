package com.prmto.person_detail_data.remote.mapper

import com.prmto.person_detail_data.remote.dto.CombinedCreditsDto
import com.prmto.person_detail_domain.model.CombinedCredits

fun CombinedCreditsDto.toCombinedCredits(): CombinedCredits {
    return CombinedCredits(
        cast = cast?.map { it.toCastForPerson() }.orEmpty(),
        crew = crew?.map { it.toCrewForPerson() }.orEmpty()
    )
}