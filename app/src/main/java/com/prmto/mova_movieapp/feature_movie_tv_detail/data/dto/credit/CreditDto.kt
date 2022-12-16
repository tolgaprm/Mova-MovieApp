package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.credit

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Credit

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