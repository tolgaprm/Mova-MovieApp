package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.credit

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.credit.CreditDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Credit

fun CreditDto.toCredit(): Credit {
    return Credit(
        cast = cast?.toCast().orEmpty(),
        crew = crew?.toCrew().orEmpty()
    )
}