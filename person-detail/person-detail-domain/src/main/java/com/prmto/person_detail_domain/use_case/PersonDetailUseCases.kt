package com.prmto.person_detail_domain.use_case

import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase

data class PersonDetailUseCases(
    val getPersonDetailUseCase: GetPersonDetailUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)