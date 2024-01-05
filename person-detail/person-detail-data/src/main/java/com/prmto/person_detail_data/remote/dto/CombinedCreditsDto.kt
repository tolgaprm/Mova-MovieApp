package com.prmto.person_detail_data.remote.dto

data class CombinedCreditsDto(
    val cast: List<CastDtoForPerson>?,
    val crew: List<CrewDtoForPerson>?
)