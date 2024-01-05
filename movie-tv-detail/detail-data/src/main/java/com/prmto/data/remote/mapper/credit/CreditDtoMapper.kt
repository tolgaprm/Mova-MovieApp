package com.prmto.data.remote.mapper.credit

import com.prmto.data.remote.dto.credit.CreditDto
import com.prmto.domain.models.credit.Credit

fun CreditDto.toCredit(): Credit {
    return Credit(
        cast = cast?.toCast().orEmpty(),
        crew = crew?.toCrew().orEmpty()
    )
}