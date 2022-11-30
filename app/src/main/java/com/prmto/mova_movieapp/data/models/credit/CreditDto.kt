package com.prmto.mova_movieapp.data.models.credit

import com.prmto.mova_movieapp.domain.models.credit.Credit

data class CreditDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>
)

fun CreditDto.toCredit(): Credit {
    return Credit(
        cast = cast.toCast(),
        crew = crew.toCrew()
    )
}