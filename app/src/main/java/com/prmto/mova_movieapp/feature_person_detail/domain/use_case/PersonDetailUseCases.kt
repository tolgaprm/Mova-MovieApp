package com.prmto.mova_movieapp.feature_person_detail.domain.use_case

import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase

data class PersonDetailUseCases(
    val getPersonDetailUseCase: GetPersonDetailUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)
