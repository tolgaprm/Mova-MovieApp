package com.prmto.core_domain.use_case.database

import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import javax.inject.Inject

class ClearAllDatabaseUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke() {
        repository.clearDatabase()
    }
}