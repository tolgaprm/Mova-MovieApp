package com.prmto.core_domain.use_case.languageIsoCode

import com.prmto.core_domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    operator fun invoke(): Flow<String> {
        return dataStoreOperations.getLanguageIsoCode()
    }
}