package com.prmto.mova_movieapp.core.domain.use_case.languageIsoCode

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    suspend operator fun invoke(languageTag: String) {
        dataStoreOperations.updateCurrentLanguageIsoCode(languageTag = languageTag.lowercase())
    }
}