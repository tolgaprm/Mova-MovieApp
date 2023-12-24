package com.prmto.mova_movieapp.core.domain.use_case.uIMode

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUIModeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {
    operator fun invoke(): Flow<Int> {
        return dataStoreOperations.getUIMode()
    }
}