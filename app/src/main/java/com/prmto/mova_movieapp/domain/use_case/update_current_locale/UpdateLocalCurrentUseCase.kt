package com.prmto.mova_movieapp.domain.use_case.update_current_locale

import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateLocalCurrentUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    suspend operator fun invoke(locale: String) {
        dataStoreOperations.updateCurrentLocale(locale = locale)
    }
}