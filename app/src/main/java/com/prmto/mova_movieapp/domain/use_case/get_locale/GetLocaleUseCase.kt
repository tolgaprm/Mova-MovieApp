package com.prmto.mova_movieapp.domain.use_case.get_locale

import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocaleUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    operator fun invoke(): Flow<String> {
        return dataStoreOperations.getLocale()
    }
}