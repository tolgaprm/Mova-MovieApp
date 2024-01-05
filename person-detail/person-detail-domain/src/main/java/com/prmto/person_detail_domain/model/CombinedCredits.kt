package com.prmto.person_detail_domain.model

data class CombinedCredits(
    val cast: List<CastForPerson>,
    val crew: List<CrewForPerson>
)