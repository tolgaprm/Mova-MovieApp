package com.prmto.core_domain.use_case.languageIsoCode

import com.prmto.core_domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    suspend operator fun invoke(languageTag: String) {
        dataStoreOperations.updateCurrentLanguageIsoCode(languageTag = languageTag.lowercase())
    }
}