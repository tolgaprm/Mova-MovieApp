package com.prmto.mova_movieapp.domain.use_case.update_current_language_iso_code

import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    suspend operator fun invoke(languageTag: String) {
        dataStoreOperations.updateCurrentLanguageIsoCode(languageTag = languageTag.lowercase())
    }
}