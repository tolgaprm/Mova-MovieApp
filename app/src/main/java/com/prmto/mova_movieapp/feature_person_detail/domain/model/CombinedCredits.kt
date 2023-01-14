package com.prmto.mova_movieapp.feature_person_detail.domain.model

data class CombinedCredits(
    val cast: List<CastForPerson>,
    val crew: List<CrewForPerson>
)
