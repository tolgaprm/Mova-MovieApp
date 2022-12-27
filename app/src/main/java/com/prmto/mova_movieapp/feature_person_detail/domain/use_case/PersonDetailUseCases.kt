package com.prmto.mova_movieapp.feature_person_detail.domain.use_case

import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase

data class PersonDetailUseCases(
    val getPersonDetailUseCase: GetPersonDetailUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)
